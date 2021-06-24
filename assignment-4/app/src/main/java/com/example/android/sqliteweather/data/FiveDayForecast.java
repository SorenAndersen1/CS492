package com.example.android.sqliteweather.data;

import androidx.room.Entity;

import com.example.android.sqliteweather.data.ForecastData;
import com.example.android.sqliteweather.data.ForecastCity;
import com.google.gson.annotations.SerializedName;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;


public class FiveDayForecast {

    private ArrayList<ForecastData> forecastDataList;

    private ForecastCity forecastCity;

    public FiveDayForecast() {
        this.forecastDataList = null;
        this.forecastCity = null;
    }

    public ArrayList<ForecastData> getForecastDataList() {
        return forecastDataList;
    }

    public ForecastCity getForecastCity() {
        return forecastCity;
    }

}
