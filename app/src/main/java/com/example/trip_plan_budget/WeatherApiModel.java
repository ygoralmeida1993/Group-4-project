package com.example.trip_plan_budget;

public class WeatherApiModel
{
    String icon;

    public WeatherApiModel() {
    }

    public WeatherApiModel(String icon, String tempMax, String tempMin, String windSpeed) {
        this.icon = icon;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        WindSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        WindSpeed = windSpeed;
    }

    String tempMax;
    String tempMin;
    String WindSpeed;
}
