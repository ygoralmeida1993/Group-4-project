package com.example.trip_plan_budget.enumeration;

public enum NearbyPlaceType {
    AMUSEMENT("amusements"),
    BEACHES("beaches"),
    RELIGIOUS("religion"),
    HIKING("climbing"),
    HISTORICAL("historic,museums,historic_architecture");

    private String type;

    NearbyPlaceType(String type) {
        this.type = type;

    }

    public String getType() {
        return type;
    }
}
