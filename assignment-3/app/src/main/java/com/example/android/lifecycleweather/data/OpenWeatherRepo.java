package com.example.android.lifecycleweather.data;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OpenWeatherRepo implements Serializable {
    @SerializedName("full_name")
    public String fullName;

    public String description;

    @SerializedName("html_url")
    public String htmlUrl;

    @SerializedName("stargazers_count")
    public int stars;
}