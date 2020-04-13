package com.example.trip_plan_budget.enumeration;

public enum PlanType {
    FLIGHT("Flight"),
    CAR("Car Trip"),
    HOTEL("Hotel"),
    RESTAURANT("Restaurant");

    private String type;

    PlanType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
