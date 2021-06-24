package com.example.android.connectedweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {
    private ArrayList<ForecastDay> mForecastData;

    private OnForecastItemClickListener mOnForecastItemClickListener;
    private ArrayList<String> mForecastStrings;

    public ForecastAdapter(OnForecastItemClickListener onForecastItemClickListener) {
        mOnForecastItemClickListener = onForecastItemClickListener;
    }

    public void updateForecastData(ArrayList<ForecastDay> forecastData, ArrayList<String> ForecastStrings) {
        mForecastData = forecastData;
        mForecastStrings = ForecastStrings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mForecastData != null && mForecastStrings != null) {
            return Math.min(mForecastData.size(), mForecastStrings.size());
        } else {
            return 0;
        }
    }

    @Override
    public ForecastItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastItemViewHolder holder, int position) {
        holder.bind(this.mForecastData.get(position));
    }

    interface OnForecastItemClickListener {
        void onForecastItemClick(ForecastDay detailedForecast);
    }

    class ForecastItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView monthTV;
        final private TextView dayTV;
        final private TextView highTempTV;
        final private TextView lowTempTV;
        final private TextView shortDescriptionTV; //just took from the notes and repurposed
        final private TextView popTV;

        public ForecastItemViewHolder(View itemView) {
            super(itemView);
            monthTV = itemView.findViewById(R.id.tv_month);
            dayTV = itemView.findViewById(R.id.tv_day);
            highTempTV = itemView.findViewById(R.id.tv_high_temp);
            lowTempTV = itemView.findViewById(R.id.tv_low_temp);
            popTV = itemView.findViewById(R.id.tv_pop);
            shortDescriptionTV = itemView.findViewById(R.id.tv_short_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnForecastItemClickListener.onForecastItemClick(mForecastData.get(getAdapterPosition()));
                }
            });
        }

        public void bind(ForecastDay forecast) {

            String superString = forecast.dt_txt.substring(5,7) + "/" + forecast.dt_txt.substring(8,10);



            monthTV.setText(superString);
            superString = forecast.dt_txt.substring(11,16);

            dayTV.setText(superString);
            int Holdlow = (int) forecast.main.temp_max;
            highTempTV.setText(Holdlow + "°F");

            String HoldPop =  (forecast.pop * 100) + "%";
            popTV.setText(HoldPop);


            Holdlow = (int) forecast.main.temp_min;
            lowTempTV.setText(Holdlow + "°F");

            shortDescriptionTV.setText(forecast.weather.get(0).description);
        }
    }
}
