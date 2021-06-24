package com.example.android.lifecycleweather.data;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.lifecycleweather.utils.OpenWeatherUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepository {
    private static final String TAG = OpenWeatherRepository.class.getSimpleName();
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private MutableLiveData<FiveDayForecast> searchResults;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentCity;
    private String currentUnits;

    private OpenWeatherSearchService gitHubService;

    public OpenWeatherRepository() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ForecastData.class, new ForecastData.JsonDeserializer())
                .registerTypeAdapter(ForecastCity.class, new ForecastCity.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.gitHubService = retrofit.create(OpenWeatherSearchService.class);

    }

    public LiveData<FiveDayForecast> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, this.currentCity)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    private boolean shouldExecuteSearch(String city, String units) {
        return !TextUtils.equals(city, this.currentCity)
                || !TextUtils.equals(units, this.currentUnits)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }


//boolean inName, boolean inDescription, boolean inReadme
    public void loadSearchResults(String city, String units) {
        if (this.shouldExecuteSearch(city, units)) {
            Log.d(TAG, "running new search for this query: " + city + ", " + units);
           this.currentCity = city;
            this.currentUnits = units;
            String queryTerm = OpenWeatherUtils.buildFiveDayForecastUrl(city, units);
            this.executeSearch(queryTerm, city, units);
        } else {
            Log.d(TAG, "using cached for this query: " + city + ", " + units);
        }
    }

    private void executeSearch(String queryTerm, String city, String units) {
        Call<FiveDayForecast> results;
        String appid = "e0b40844a7d68ebb8344f8a9777ee6f5";
        results = this.gitHubService.searchRepos(city, units, appid);

        this.searchResults.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);
        results.enqueue(new Callback<FiveDayForecast>() {
            @Override
            public void onResponse(Call<FiveDayForecast> call, Response<FiveDayForecast> response) {

                if (response.code() == 200) {
                    Log.d(TAG, "SUCCESS");
                        searchResults.setValue(response.body());
                        loadingStatus.setValue(LoadingStatus.SUCCESS);

                } else {
                    Log.d(TAG, "FAILURE");

                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<FiveDayForecast> call, Throwable t) {
                Log.d(TAG, "FAILURE");
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }
}