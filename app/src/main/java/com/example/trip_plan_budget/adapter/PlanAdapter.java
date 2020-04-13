package com.example.trip_plan_budget.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedList;

import com.example.trip_plan_budget.adapter.viewHolder.FlightPlanViewHolder;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.example.trip_plan_budget.service.AuthService;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlanAdapter {
    private static PlanAdapter instance;
    private Query flightQuery, hotelQuery, carQuery, restQuery;
    private PagedList.Config pageConfig;

    //    private DatabasePagingOptions<> flightOptions;
//    private DatabasePagingOptions<FlightModel> flightOptions;
//    private DatabasePagingOptions<FlightModel> flightOptions;
    private DatabasePagingOptions<FlightModel> flightOptions;

    private FirebaseRecyclerPagingAdapter<FlightModel, FlightPlanViewHolder> flightPlanAdapter;

    private PlanAdapter(LifecycleOwner lifecycleOwner) {
        String uid = AuthService.getInstance().getID();

        carQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("id").equalTo("car");
        flightQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("id").equalTo("flight");
        hotelQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("id").equalTo("hotel");
        restQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("id").equalTo("restaurant");

        pageConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build();
        flightOptions = new DatabasePagingOptions.Builder<FlightModel>()
                .setLifecycleOwner(lifecycleOwner)
                .setQuery(flightQuery, pageConfig, FlightModel.class)
                .build();


    }

    public static PlanAdapter getInstance(LifecycleOwner owner) {
        if (instance == null) instance = new PlanAdapter(owner);
        return instance;
    }

    private void setAdapter() {
        flightPlanAdapter =
                new FirebaseRecyclerPagingAdapter<FlightModel, FlightPlanViewHolder>(flightOptions) {

                    @NonNull
                    @Override
                    public FlightPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return null;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FlightPlanViewHolder viewHolder, int position, @NonNull FlightModel model) {
                        viewHolder.bind(model);
                    }

                    @Override
                    protected void onLoadingStateChanged(@NonNull LoadingState state) {

                    }
                };
    }

    public FirebaseRecyclerPagingAdapter getFlightPlanAdapter() {
        return flightPlanAdapter;
    }
}
