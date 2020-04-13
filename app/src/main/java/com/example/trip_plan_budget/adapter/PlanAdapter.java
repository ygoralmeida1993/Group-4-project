package com.example.trip_plan_budget.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedList;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.plan.PlanSummaryActivity;
import com.example.trip_plan_budget.adapter.viewHolder.FlightPlanViewHolder;
import com.example.trip_plan_budget.enumeration.EnumUtil;
import com.example.trip_plan_budget.enumeration.PlanType;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.example.trip_plan_budget.service.AuthService;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlanAdapter {
    private static final String TAG = "PlanAdapter";
    private static PlanAdapter instance;
    private Query flightQuery, hotelQuery, carQuery, restQuery;
    private PagedList.Config pageConfig;

    //    private DatabasePagingOptions<> flightOptions;
//    private DatabasePagingOptions<FlightModel> flightOptions;
//    private DatabasePagingOptions<FlightModel> flightOptions;
    private DatabasePagingOptions<FlightModel> flightOptions;

    private FirebaseRecyclerPagingAdapter<FlightModel, FlightPlanViewHolder> flightPlanAdapter;

    private PlanAdapter() {
        String uid = AuthService.getInstance().getID();
        carQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("type").equalTo("car");
        flightQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans");
        hotelQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("type").equalTo("hotel");
        restQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid).child("plans").orderByChild("type").equalTo("restaurant");

        pageConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build();


    }

    public static PlanAdapter getInstance() {
        if (instance == null) instance = new PlanAdapter();
        return instance;
    }

    public FirebaseRecyclerPagingAdapter getFlightPlanAdapter(Context context, LifecycleOwner owner) {
        flightOptions = new DatabasePagingOptions.Builder<FlightModel>()
                .setLifecycleOwner(owner)
                .setQuery(flightQuery, pageConfig, FlightModel.class)
                .build();
        flightPlanAdapter =
                new FirebaseRecyclerPagingAdapter<FlightModel, FlightPlanViewHolder>(flightOptions) {

                    @NonNull
                    @Override
                    public FlightPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new FlightPlanViewHolder(LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_flight_plan_item, parent, false));
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FlightPlanViewHolder viewHolder, int position, @NonNull FlightModel model) {
                        viewHolder.bind(model, () -> {
                            Intent intent = new Intent(context, PlanSummaryActivity.class);
                            intent.putExtra("model", model);
                            EnumUtil.serialize(PlanType.FLIGHT).to(intent);
                            context.startActivity(intent);
                            Log.v(TAG, "FlightPlan : " + model.getDeparture().getPlaceName());
                        });
                    }

                    @Override
                    protected void onLoadingStateChanged(@NonNull LoadingState state) {

                    }
                };
        return flightPlanAdapter;
    }


}
