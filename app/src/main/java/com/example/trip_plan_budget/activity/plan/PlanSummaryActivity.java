package com.example.trip_plan_budget.activity.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.activity.places.NearbyPlacesActivity;
import com.example.trip_plan_budget.databinding.ActivityPlanSummaryBinding;
import com.example.trip_plan_budget.enumeration.EnumUtil;
import com.example.trip_plan_budget.enumeration.PlanType;
import com.example.trip_plan_budget.model.flight.FlightModel;

public class PlanSummaryActivity extends AppCompatActivity {
    private ActivityPlanSummaryBinding binding;
    private PlanType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        type = EnumUtil.deserialize(PlanType.class).from(getIntent());
        if (type != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(type.getType() + " Plan");
            }
            FlightModel flightModel;
            switch (type) {
                case FLIGHT:
                    flightModel = getIntent().getParcelableExtra("model");
//                    binding.planDetails.setOnClickListener(v -> {
//                        Intent intent = new Intent(PlanSummaryActivity.this, PlanDetailsActivity.class);
//                        intent.putExtra("model", flightModel);
//                        EnumUtil.serialize(type).to(intent);
//                        startActivity(intent);
//                    });
                    binding.planDetails.setVisibility(View.GONE);
                    binding.nearbyPlaces.setOnClickListener(v -> {
                        Intent intent = new Intent(PlanSummaryActivity.this, NearbyPlacesActivity.class);
                        intent.putExtra("placeName", flightModel.getLanding().getPlaceName());
                        startActivity(intent);
                    });
                    break;
            }

        }

    }
}
