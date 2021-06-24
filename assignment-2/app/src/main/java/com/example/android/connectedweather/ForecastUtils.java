package com.example.android.connectedweather;
import android.net.Uri;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ForecastUtils {
    final static String FORECAST_SEARCH_BASE_URL =
            "https://api.openweathermap.org/data/2.5/forecast";
    final static String FORECAST_SEARCH_QUERY_PARAM = "q";
    final static String FORECAST_SEARCH_SORT_PARAM = "Sedro-Woolley";
    final static String FORECAST_SEARCH_APP_ID = "appid";
    final static String FORECAST_SEARCH_API_KEY= "e0b40844a7d68ebb8344f8a9777ee6f5";
    final static String FORECAST_SEARCH_UNITS_OPTION = "units";
    final static String FORECAST_SEARCH_UNITS = "imperial";

//setup for
    public static class ForecastSearchResults {
        ArrayList<ForecastDay> list;
    }

    public static String buildForecastSearchURL() {
        return Uri.parse(FORECAST_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(FORECAST_SEARCH_QUERY_PARAM, FORECAST_SEARCH_SORT_PARAM)
                .appendQueryParameter(FORECAST_SEARCH_APP_ID, FORECAST_SEARCH_API_KEY)
                .appendQueryParameter(FORECAST_SEARCH_UNITS_OPTION, FORECAST_SEARCH_UNITS)
                .build()
                .toString(); //simple creating URL
    }


    public static ArrayList<ForecastDay> parseForecastSearchResults(String json) {
        Gson gson = new Gson();
        ForecastSearchResults results = gson.fromJson(json, ForecastSearchResults.class);
        if (results != null && results.list != null) {
            return results.list; //actually send it
        } else {
            return null;
        }
    }
}