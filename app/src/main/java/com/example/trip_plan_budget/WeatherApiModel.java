package com.example.trip_plan_budget;

public class WeatherApiModel
{
    String icon;
    double tempMax;
    double tempMin;
    double WindSpeed;

    public WeatherApiModel(String icon, double tempMax, double tempMin, double windSpeed) {
        this.icon = icon;
        this.tempMax = (tempMax-32)*1.8;//converting in to celcius
        this.tempMin = (tempMin-32)*1.8;
        WindSpeed = windSpeed;
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
}
