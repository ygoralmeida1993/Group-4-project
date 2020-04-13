package com.example.trip_plan_budget.interfaces.endpoint;

import com.example.trip_plan_budget.model.hotel.HotelModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TripAdvisorEndpoint {
    @Headers({
            "x-rapidapi-host: tripadvisor1.p.rapidapi.com",
            "x-rapidapi-key: 7373551c59msh43f6546f0df1e85p1b4120jsn0639fc85a529"
    })
    @GET("hotels/list-by-latlng")
    Call<List<HotelModel>> getHotels(
            @Query("adults") int adults,
            @Query("rooms") int rooms,
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("currency") String currency,
            @Query("checkin") String checkin,
            @Query("nights") int nights);

    @Headers({
            "x-rapidapi-host: tripadvisor1.p.rapidapi.com",
            "x-rapidapi-key: 7373551c59msh43f6546f0df1e85p1b4120jsn0639fc85a529"
    })
    @GET("restaurants/list-by-latlng")
    Call<List<HotelModel>> getRestaurants(
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("currency") String currency);
}
