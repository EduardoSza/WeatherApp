package com.example.findinglogs.model;

public class CityHistory {
    private String cityName;
    private long timestamp;

    public CityHistory(String cityName, long timestamp) {
        this.cityName = cityName;
        this.timestamp = timestamp;
    }

    public String getCityName() {
        return cityName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
