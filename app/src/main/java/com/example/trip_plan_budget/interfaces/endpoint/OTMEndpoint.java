package com.example.trip_plan_budget.interfaces.endpoint;

import com.example.trip_plan_budget.model.places.PlaceModel;
import com.example.trip_plan_budget.model.places.Point;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OTMEndpoint {


    @GET("places/radius")
    Call<List<PlaceModel>> getPlaces(@Query("radius") int radius,
                                     @Query("lat") double lat,
                                     @Query("lon") double lon,
                                     @Query("kinds") String kinds,
                                     @Query("format") String format,
                                     @Query("apikey") String key);

    @GET("places/geoname")
    Call<Point> getPlacePoint(@Query("name") String name,
                              @Query("country") String country,
                              @Query("apikey") String key);
}
