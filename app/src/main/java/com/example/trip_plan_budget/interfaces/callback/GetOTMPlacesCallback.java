package com.example.trip_plan_budget.interfaces.callback;

import com.example.trip_plan_budget.model.places.PlaceModel;

import java.util.List;

public interface GetOTMPlacesCallback {
    void onResult(List<PlaceModel> models);
}
