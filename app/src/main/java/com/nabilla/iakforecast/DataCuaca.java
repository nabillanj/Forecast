package com.nabilla.iakforecast;

public class DataCuaca {
    private Double dayTemperature;
    private String date;
    private String weather;
    private String description;

    public Double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(Double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
