package com.example.trip_plan_budget.service;


import android.content.res.Resources;
import android.util.Log;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.interfaces.callback.GetOTMPlacesCallback;
import com.example.trip_plan_budget.interfaces.callback.GetOTMPointCallback;
import com.example.trip_plan_budget.interfaces.endpoint.OTMEndpoint;
import com.example.trip_plan_budget.model.places.PlaceModel;
import com.example.trip_plan_budget.model.places.Point;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String TAG = "NetworkService";
    private static NetworkService instance;
    private String apiOTM = Resources.getSystem().getString(R.string.url_opentripmap);

    private NetworkService() {

    }

    public static NetworkService getInstance() {
        if (instance == null) instance = new NetworkService();
        return instance;
    }

    public void getPlacesInRadius(int radius, double lat, double lon, String kinds, GetOTMPlacesCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Resources.getSystem().getString(R.string.url_opentripmap))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OTMEndpoint endpoint = retrofit.create(OTMEndpoint.class);

        Call<List<PlaceModel>> places = endpoint.getPlaces(radius, lat, lon, kinds, "json", apiOTM);
        places.enqueue(new Callback<List<PlaceModel>>() {
            @Override
            public void onResponse(Call<List<PlaceModel>> call, Response<List<PlaceModel>> response) {
                Log.v(TAG, "Places : " + response.body().size());
                List<PlaceModel> places = response.body().stream().filter(place -> !place.getName().isEmpty() && place.getPoint() != null).collect(Collectors.toList());
                callback.onResult(places);
            }

            @Override
            public void onFailure(Call<List<PlaceModel>> call, Throwable t) {
                callback.onResult(null);
            }
        });
    }

    public void getPlacesPoint(String name, GetOTMPointCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Resources.getSystem().getString(R.string.url_opentripmap))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OTMEndpoint endpoint = retrofit.create(OTMEndpoint.class);

        Call<Point> point = endpoint.getPlacePoint(name, "CA", apiOTM);
        point.enqueue(new Callback<Point>() {
            @Override
            public void onResponse(Call<Point> call, Response<Point> response) {
                if (response.body() != null) {
                    callback.onResult(response.body());
                } else callback.onResult(null);
            }

            @Override
            public void onFailure(Call<Point> call, Throwable t) {
                callback.onResult(null);
            }
        });
    }

}
