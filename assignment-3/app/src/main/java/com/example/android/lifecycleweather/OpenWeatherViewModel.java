package com.example.android.lifecycleweather;

import com.example.android.lifecycleweather.data.FiveDayForecast;
import com.example.android.lifecycleweather.data.ForecastData;
import com.example.android.lifecycleweather.data.OpenWeatherRepository;
import com.example.android.lifecycleweather.data.LoadingStatus;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OpenWeatherViewModel extends ViewModel {
    private LiveData<FiveDayForecast> searchResults;
    private LiveData<LoadingStatus> loadingStatus;
    private OpenWeatherRepository repository;

    public OpenWeatherViewModel() {
        this.repository = new OpenWeatherRepository();
        this.searchResults = this.repository.getSearchResults();
        this.loadingStatus = this.repository.getLoadingStatus();
    }



    public void loadSearchResults(String city, String units) {
        this.repository.loadSearchResults(city, units);
    }

    public LiveData<FiveDayForecast> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }
}