package com.example.android.connectedweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mForecastFullViews;
    private ForecastAdapter mForecastAdapter;
    private ProgressBar mProgressBar;
    private TextView mErrorMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mForecastFullViews = findViewById(R.id.rv_forecast_list);

        mForecastFullViews.setLayoutManager(new LinearLayoutManager(this));
        mForecastFullViews.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);
        mForecastFullViews.setAdapter(mForecastAdapter);

        mProgressBar = findViewById(R.id.pb_loading_indicator); //setup
        mErrorMessageTV = findViewById(R.id.tv_error_message);
        doForecast();
    }

    @Override
    public void onForecastItemClick(ForecastDay detailedForecast) {
        Intent ForecastActivityIntent = new Intent(this, ForecastDetailActivity.class);
        ForecastActivityIntent.putExtra(ForecastDetailActivity.EXTRA_FORECAST, detailedForecast);
        startActivity(ForecastActivityIntent);
    }

    private void doForecast() {
        String url = ForecastUtils.buildForecastSearchURL();
        Log.d(TAG, "query URL: " + url); //actually sending url once set up
        new ForecastSearchTask().execute(url);
    }

    public class ForecastSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String results = null;
            try {
                results = NetworkUtils.doHTTPGet(url); //notes simple url
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.INVISIBLE);
            if(s != null) {
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                mForecastFullViews.setVisibility(View.VISIBLE);
                ArrayList<ForecastDay> searchResultsList = ForecastUtils.parseForecastSearchResults(s);
                mForecastAdapter.updateForecastData(searchResultsList,new ArrayList<String>(Arrays.asList(longForecasts))); //gotten from notes
            } else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
                mForecastFullViews.setVisibility(View.INVISIBLE);
            }
        }
    }

    private static final String[] longForecasts = {

            "Fairly calm rain showers spread throughout the day, with a high of 15C and a low of 4C\n",
            "Harsh dark clouds move in to the valley today, with a high of 21C and a low of 7C\n",
            "Massive blizzard painting the valley white today, with a high of 1C and a low of -15C\n",
            "Light snow with a chance of rain, high of 5C and a low of -10C\n",
            "A small blizzard is covering the valley today, with a high of -1C and a low of -17C\n",
            "Beautiful sunny day, not a cloud in the sky with a low of 15C and a high of 25C\n",
            "Terrible cracks of electricity plague the air today, with a high of 25C and a low of 10C\n",
            "The clouds block out God today, luckily there is a high of 5C and a low of 4C\n",
            "The rain will come down throughout today, be careful in those cars! There is a high of 10C and low of 3C\n",
            "The sun shines down on the people's faces today, with a high of 15C and a low of 12C\n",
            "The Sun has commanded the sky today, bringing in highs of 100C! With a low of 99C.\n",
            "The beautiful cloudless sky is bringing in highs of 35C and a low of 15C.\n"
    };

}