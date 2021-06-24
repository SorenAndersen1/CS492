package com.example.android.basicweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements WeatherAdapter.OnWeatherChangeListener {
    private RecyclerView mWeatherRV;
    private WeatherAdapter mWeatherAdapter;
    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //given code

        mWeatherRV = findViewById(R.id.rv_weather_list); //find ID by item
        mWeatherRV.setLayoutManager(new LinearLayoutManager(this)); //take context to setup layout of app
        mWeatherRV.setHasFixedSize(true); //We arent fancy yet


        mWeatherAdapter = new WeatherAdapter(this); //toast listener, exactly like javascript and css button
        mWeatherRV.setAdapter(mWeatherAdapter);


        ArrayList<String> weatherString = new ArrayList<>(); //always allocate arrays
        weatherString = initializeWeather(); //intialize (dummy values)
        displayWeather(weatherString); //display boxes of weather text

        ArrayList<String> toastWeather = new ArrayList<>();
        toastWeather = initializeToast(); //intialize toast (dummy values with long descripitions)
        displayToast(toastWeather); //display toast

        mToast = null; //set toast = NULL we dont know which one the user has clicked yet (hasnt happened)


    }

    public ArrayList<String> initializeWeather() {
        ArrayList<String> weatherDescr = new ArrayList<>();
        //for formatting sakes I had to use P. instead of percip. when the text took up too much space
        weatherDescr.add("1/25/21 | Rain Shower | 15C | 99% p.\n");
        weatherDescr.add("1/26/21 | Cloudy | 20C | 25% precip.\n");
        weatherDescr.add("1/28/21 | Blizzard | 0C | 95% precip.\n");
        weatherDescr.add("1/29/21 | Light Snow | -2C | 92% p.\n");
        weatherDescr.add("1/30/21 | Blizzard | -27C | 98% precip.\n");
        weatherDescr.add("1/31/21 | Sunny | 25C | 5% precip.\n");
        weatherDescr.add("2/1/21 | Lightining Storm | 4C | 2% p.\n");
        weatherDescr.add("2/2/21 |  Cloudy | 13C | 30% precip.\n");
        weatherDescr.add("2/3/21 |  Rain Shower | 10C | 94% p. \n");
        weatherDescr.add("2/4/21 |  Sunny | 20C | 0% precip.\n");
        weatherDescr.add("2/5/21 |  Sunny | 100C | 30% precip.\n");
        weatherDescr.add("2/6/21 |  Sunny | 35C | 3% precip.\n");

        return weatherDescr; //dummy values tried to format the same

    }

    public ArrayList<String> initializeToast() {
        ArrayList<String> weatherToast = new ArrayList<>();

        weatherToast.add("Fairly calm rain showers spread throughout the day, with a high of 15C and a low of 4C\n");
        weatherToast.add("Harsh dark clouds move in to the valley today, with a high of 21C and a low of 7C\n");
        weatherToast.add("Massive blizzard painting the valley white today, with a high of 1C and a low of -15C\n");
        weatherToast.add("Light snow with a chance of rain, high of 5C and a low of -10C\n");
        weatherToast.add("A small blizzard is covering the valley today, with a high of -1C and a low of -17C\n");
        weatherToast.add("Beautiful sunny day, not a cloud in the sky with a low of 15C and a high of 25C\n");
        weatherToast.add("Terrible cracks of electricity plague the air today, with a high of 25C and a low of 10C\n");
        weatherToast.add("The clouds block out God today, luckily there is a high of 5C and a low of 4C\n");
        weatherToast.add("The rain will come down throughout today, be careful in those cars! There is a high of 10C and low of 3C\n");
        weatherToast.add("The sun shines down on the people's faces today, with a high of 15C and a low of 12C\n");
        weatherToast.add("The Sun has commanded the sky today, bringing in highs of 100C! With a low of 99C.\n");
        weatherToast.add("The beautiful cloudless sky is bringing in highs of 35C and a low of 15C.\n");

        return weatherToast; //longer descriptions

    }
    public void displayWeather(ArrayList<String> weatherDescr) {

            mWeatherAdapter.addWeather(weatherDescr.get(0));
            mWeatherAdapter.addWeather(weatherDescr.get(1));
            mWeatherAdapter.addWeather(weatherDescr.get(2));
            mWeatherAdapter.addWeather(weatherDescr.get(3));
            mWeatherAdapter.addWeather(weatherDescr.get(4));
            mWeatherAdapter.addWeather(weatherDescr.get(5));
            mWeatherAdapter.addWeather(weatherDescr.get(6));
            mWeatherAdapter.addWeather(weatherDescr.get(7));
            mWeatherAdapter.addWeather(weatherDescr.get(8));
            mWeatherAdapter.addWeather(weatherDescr.get(9));
            mWeatherAdapter.addWeather(weatherDescr.get(10));
            mWeatherAdapter.addWeather(weatherDescr.get(11));
    //intialize function, just add all the strings to the adapter
    }


    public void displayToast(ArrayList<String> mToast) {

            mWeatherAdapter.addToast(mToast.get(0));
            mWeatherAdapter.addToast(mToast.get(1));
            mWeatherAdapter.addToast(mToast.get(2));
            mWeatherAdapter.addToast(mToast.get(3));
            mWeatherAdapter.addToast(mToast.get(4));
            mWeatherAdapter.addToast(mToast.get(5));
            mWeatherAdapter.addToast(mToast.get(6));
            mWeatherAdapter.addToast(mToast.get(7));
            mWeatherAdapter.addToast(mToast.get(8));
            mWeatherAdapter.addToast(mToast.get(9));
            mWeatherAdapter.addToast(mToast.get(10));
            mWeatherAdapter.addToast(mToast.get(11));
            //intalize func, adds all toast to weather adapter
    }


    @Override
    public void onWeatherChanged(String toastText) {
        if(mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, toastText, Toast.LENGTH_LONG); //taken from notes
        mToast.show();
    }

}
