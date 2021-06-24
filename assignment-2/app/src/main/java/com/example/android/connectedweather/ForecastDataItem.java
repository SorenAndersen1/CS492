package com.example.android.connectedweather;

import java.util.Calendar;
import java.util.Locale;

public class ForecastDataItem {
    private Calendar date;
    private int highTemp;
    private int lowTemp;
    private double pop;
    private String shortDescription;
    private String longDescription;

    public ForecastDataItem(int year, int month, int dayOfMonth, int highTemp, int lowTemp,
                            double pop, String shortDescription, String longDescription) {
        this.date = Calendar.getInstance();
        this.date.set(year, month, dayOfMonth);
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.pop = pop;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public String getMonthString() {
        return date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

    public String getDayString() {
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

    public int getHighTemp() {
        return highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public double getPop() {
        return pop;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }
}
