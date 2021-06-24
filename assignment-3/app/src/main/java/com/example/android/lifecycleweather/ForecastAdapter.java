package com.example.android.lifecycleweather;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.lifecycleweather.data.FiveDayForecast;
import com.example.android.lifecycleweather.data.ForecastData;
import com.example.android.lifecycleweather.utils.OpenWeatherUtils;

import java.util.Date;
import java.util.*;
import java.text.*;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {
    private FiveDayForecast searchResultsList;
    private OnForecastItemClickListener ForecastItemClickListener;
    private static final String TAG = MainActivity.class.getSimpleName();

    public interface OnForecastItemClickListener {
        void onForecastItemClick(ForecastData forecastData);
    }

    public ForecastAdapter(OnForecastItemClickListener onForecastItemClickListener) {
        this.ForecastItemClickListener = onForecastItemClickListener;
    }

    @NonNull
    @Override
    public ForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastItemViewHolder holder, int position) {
        holder.bind(this.searchResultsList.getForecastDataList().get(position));
    }

    public void updateForecastData(FiveDayForecast fiveDayForecast) {
        searchResultsList = fiveDayForecast;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.searchResultsList == null || this.searchResultsList.getForecastDataList() == null) {
            return 0;
        } else {
            return this.searchResultsList.getForecastDataList().size();
        }
    }

    class ForecastItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView dateTV;
        final private TextView timeTV;
        final private TextView highTempTV;
        final private TextView lowTempTV;
        final private TextView popTV;
        final private ImageView iconIV;


        public ForecastItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.tv_date);
            timeTV = itemView.findViewById(R.id.tv_time);
            highTempTV = itemView.findViewById(R.id.tv_high_temp);
            lowTempTV = itemView.findViewById(R.id.tv_low_temp);
            popTV = itemView.findViewById(R.id.tv_pop);
            iconIV = itemView.findViewById(R.id.iv_forecast_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForecastItemClickListener.onForecastItemClick(
                            searchResultsList.getForecastDataList().get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(ForecastData repo) {
            Context ctx = this.itemView.getContext();

            Calendar date = OpenWeatherUtils.dateFromEpochAndTZOffset(
                    repo.getEpoch(),
                    searchResultsList.getForecastCity().getTimezoneOffsetSeconds()
            );
            dateTV.setText(ctx.getString(R.string.forecast_date, date));
            timeTV.setText(ctx.getString(R.string.forecast_time, date));
            String temp = repo.getHighTemp() + " ";
           //
            // temp = currentCity
            this.highTempTV.setText(temp);

            temp = repo.getLowTemp() + " "; //+ ctx.getString(R.string.pref_temp_key);;
            this.lowTempTV.setText(temp);
            popTV.setText(ctx.getString(R.string.forecast_pop, repo.getPop()));

            Glide.with(this.itemView.getContext())
                    .load(repo.getIconUrl())
                    .into(iconIV);

        }
    }
}
