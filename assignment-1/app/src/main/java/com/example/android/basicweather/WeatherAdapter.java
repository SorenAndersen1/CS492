package com.example.android.basicweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {
    private ArrayList<String> mWeatherList = new ArrayList<>(); //allocate

    private ArrayList<String> mToasts = new ArrayList<>();
    private OnWeatherChangeListener mListener; //listener for toast

    public interface OnWeatherChangeListener {
        void onWeatherChanged(String todoText); //holdover from class notes text
    }


    @Override
    public int getItemCount() {
        return mWeatherList.size(); //simple size, taken from notes
    }


    public void addWeather(String weather) {
        mWeatherList.add(0, weather);
        notifyItemInserted(0); //add something when we can and notifty
    }

    public void addToast(String toast) {
        mToasts.add(0, toast); //add toast to screen if nesseecary
        notifyItemInserted(0);
    }


    public WeatherAdapter(OnWeatherChangeListener listener) {
        mWeatherList = new ArrayList<>();
        mToasts = new ArrayList<>();
        mListener = listener; //reset everything on listener
    }


    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.weather_item, parent, false); //taken from notes
        return new WeatherHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        String weather = mWeatherList.get(position);
        holder.bind(weather);
    }

    class WeatherHolder extends RecyclerView.ViewHolder {
        private TextView mWeatherItemsTV;

        private WeatherHolder(final View itemView) {
            super(itemView);
            mWeatherItemsTV = itemView.findViewById(R.id.tv_weather_items);
            mWeatherItemsTV.setOnClickListener(new CompoundButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String toastText = mToasts.get(getAdapterPosition()); //function changed over from class notes
                    mListener.onWeatherChanged(toastText);
                }
            });

        }


        void bind(String text) {
            mWeatherItemsTV.setText(text); //simple bind function, adds text to item of list
        }
    }

}

