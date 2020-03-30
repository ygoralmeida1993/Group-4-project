package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherApiModel implements Parcelable {
    public static final Creator<WeatherApiModel> CREATOR = new Creator<WeatherApiModel>() {
        @Override
        public WeatherApiModel createFromParcel(Parcel in) {
            return new WeatherApiModel(in);
        }

        @Override
        public WeatherApiModel[] newArray(int size) {
            return new WeatherApiModel[size];
        }
    };
    private String icon;
    private double tempMax;
    private double tempMin;
    private double WindSpeed;

    public WeatherApiModel(String icon, double tempMax, double tempMin, double windSpeed) {
        this.icon = icon;
        this.tempMax = (tempMax - 32) * 1.8;//converting in to celcius
        this.tempMin = (tempMin - 32) * 1.8;
        WindSpeed = windSpeed;
    }

    protected WeatherApiModel(Parcel in) {
        icon = in.readString();
        tempMax = in.readDouble();
        tempMin = in.readDouble();
        WindSpeed = in.readDouble();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        WindSpeed = windSpeed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeDouble(tempMax);
        dest.writeDouble(tempMin);
        dest.writeDouble(WindSpeed);
    }
}
