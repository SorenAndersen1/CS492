package com.example.android.lifecycleweather.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.example.android.lifecycleweather.data.FiveDayForecast;
import com.example.android.lifecycleweather.data.ForecastCity;
import com.example.android.lifecycleweather.data.ForecastData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.TimeZone;

public class OpenWeatherUtils {
    private static final String FIVE_DAY_FORECAST_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String FIVE_DAY_FORECAST_CITY_PARAM = "q";
    private static final String FIVE_DAY_FORECAST_UNITS_PARAM = "units";
    private static final String FIVE_DAY_FORECAST_APPID_PARAM = "appid";

    private final static String FIVE_DAY_FORECAST_DEFAULT_TIMEZONE = "UTC";
    private final static String TIMEZONE_OFFSET_FORMAT_STR = "GMT%0+3d:%02d";

    /**
     * Builds a URL to query the OpenWeather API to fetch the 5 day/3 hour forecast for a specified
     * city.
     *
     * @param city The name of the city for which to fetch the forecast, e.g. "Corvallis,OR,US".
     * @param units String specifying the type of units of measurement to fetch.  Can be
     *              "imperial", "metric", or "standard".
     * @param appId The OpenWeather API key to use to fetch the forecast.
     *
     * @return Returns a String version of the OpenWeather API URL constructed using the provided
     * parameters.
     */
    public static String buildFiveDayForecastUrl(String city, String units) {
        String queryTerm = new String(city);
/*
        if (!TextUtils.isEmpty(units)) {
            queryTerm += " " + String.format(GITHUB_SEARCH_LANGUAGE_FORMAT_STR, language);
        }

        if (!TextUtils.isEmpty(user)) {
            queryTerm += " " + String.format(GITHUB_SEARCH_USER_FORMAT_STR, user);
        }

        String searchIn = buildSearchInTerm(inName, inDescription, inReadme);
        if (!TextUtils.isEmpty(searchIn)) {
            queryTerm += " " + String.format(GITHUB_SEARCH_IN_FORMAT_STR, searchIn);
        }
*/
        queryTerm += " " + units;
        return queryTerm;
    }

    /**
     * Converts a Unix epoch time (e.g. `dt` from the OpenWeather API) plus a timezone offset
     * to a calendar with the correct timezone for the specified offset.
     *
     * @param epoch A Unix epoch timestamp, in seconds.
     * @param tzOffsetSeconds A timezone offset, in seconds.
     *
     * @return Returns a Calendar object representing the time specified by `epoch`, with the
     * timezone correctly set to the one specified by `tzOffsetSeconds`.
     */
    public static Calendar dateFromEpochAndTZOffset(int epoch, int tzOffsetSeconds) {
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone(FIVE_DAY_FORECAST_DEFAULT_TIMEZONE));
        date.setTimeInMillis(epoch * 1000L);
        int tzOffsetHours = tzOffsetSeconds / 3600;
        int tzOffsetMin = (Math.abs(tzOffsetSeconds) % 3600) / 60;
        String localTimezoneId = String.format(TIMEZONE_OFFSET_FORMAT_STR, tzOffsetHours, tzOffsetMin);
        date.setTimeZone(TimeZone.getTimeZone(localTimezoneId));
        return date;
    }

    /**
     * Parses the JSON response from the OpenWeather API's 5 day/3 hour forecast API.
     *
     * @param fiveDayForecastJson The raw JSON response from the OpenWeather 5 day/3 hour API.
     *
     * @return Returns a FiveDayForecast object representing the fetched forecast.
     */
    public static FiveDayForecast parseFiveDayForecastResponse(String fiveDayForecastJson) {
        /*
         * In the Gson parsing below, custom deserializers are registered to parse elements of the
         * OpenWeather API's JSON response directly into objects of classes whose structures do
         * not directly match the JSON string.
         *
         * Note that a custom `Gson` object can be passed into Retrofit's
         * `GsonConverterFactory.create()`, like so:
         *
         *    Gson gson = new GsonBuilder().
         *        ...
         *        .create();
         *    Retrofit retrofit = new Retrofit.Builder()
         *        .addConverterFactory(GsonConverterFactory.create(gson)
         *        ...
         *        .build();
         *
         * This means you can use these custom deserializers in conjunction with Retrofit, too.
         */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ForecastData.class, new ForecastData.JsonDeserializer())
                .registerTypeAdapter(ForecastCity.class, new ForecastCity.JsonDeserializer())
                .create();
        return gson.fromJson(fiveDayForecastJson, FiveDayForecast.class);
    }
}
