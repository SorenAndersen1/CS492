package com.example.android.connectedweather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

public class ForecastDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FORECAST = "Forecast "; //set up
    private ForecastDay mForecast; //taken from notes

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_list_item);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_FORECAST)) {
            mForecast = (ForecastDay) intent.getSerializableExtra(EXTRA_FORECAST); //TAKEN FROM NOTES


            TextView forecastMonthTv = findViewById(R.id.tv_month);
            String superString = mForecast.dt_txt.substring(5,7) + "/" + mForecast.dt_txt.substring(8,10);

                    forecastMonthTv.setText(superString);

            TextView forecastDayTv = findViewById(R.id.tv_day);
            superString = mForecast.dt_txt.substring(11,16);
            forecastDayTv.setText((superString));

            TextView forecastPopTv= findViewById(R.id.tv_pop);
            int Holdpop = (int) mForecast.pop * 100;
            String displayHoldpop =  (Holdpop) + "%"; //formatting
            forecastPopTv.setText(displayHoldpop);



            TextView forecastLowTemp = findViewById(R.id.tv_low_temp);
            int Holdlow = (int) mForecast.main.temp_min;
            String displayTempLow =  Holdlow + " F"; //formatting
            forecastLowTemp.setText(displayTempLow);

            TextView forecastHighTemp = findViewById(R.id.tv_high_temp);
            int HoldHigh = (int) mForecast.main.temp_max;
            String displayTempHigh =  HoldHigh + " F";//formatting
            forecastHighTemp.setText(displayTempHigh);



            TextView forecastDescriptionTV = findViewById(R.id.tv_short_description);

            forecastDescriptionTV.setText("Conditions: " + mForecast.weather.get(0).description); //JAVA

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast_async, menu); //taken from notes
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_view_area: //notes function just name
                viewAreaOnApp();
                return true;

            case R.id.action_share:
                shareWeather();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void shareWeather() {
        if(mForecast != null) {
            String shareText = "Conditions: " + mForecast.weather.get(0).description;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");
            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
    }

    public void viewAreaOnApp() {
        if (mForecast != null) {
            String uri = String.format(Locale.ENGLISH, "geo:48.5039, 122.2361"); //sedro woulley
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            PackageManager pm = getPackageManager(); //taken from notes

            List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            if(activities.size() > 0) {
                startActivity(intent);
            }
        }
    }


}