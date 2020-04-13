package com.example.trip_plan_budget.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.databinding.ActivityPlanDetailsBinding;

public class PlanDetailsActivity extends AppCompatActivity {
    private ActivityPlanDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nearbyPlaces.setOnClickListener(v -> {
            startActivity(new Intent(PlanDetailsActivity.this, null));
        });
    }
}
