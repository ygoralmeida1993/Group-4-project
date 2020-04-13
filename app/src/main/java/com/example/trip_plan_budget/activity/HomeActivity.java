package com.example.trip_plan_budget.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trip_plan_budget.activity.car.CarBudgetPlannerActivity;
import com.example.trip_plan_budget.activity.flight.FlightPlannerActivity;
import com.example.trip_plan_budget.adapter.HomeFragmentPagerAdapter;
import com.example.trip_plan_budget.adapter.PlanAdapter;
import com.example.trip_plan_budget.databinding.ActivityHomeBinding;
import com.example.trip_plan_budget.enumeration.PlanType;
import com.example.trip_plan_budget.model.main.UserModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {
    private UserModel user;

    private ActivityHomeBinding binding;
    private HomeFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PlanAdapter.getInstance(this);

        adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            String text = "";
            switch (position) {
                case 0:
                    text = PlanType.CAR.getType();
                    tab.setText(text);
                    break;
                case 1:
                    text = PlanType.FLIGHT.getType();
                    tab.setText(text);
                    break;
                case 2:
                    text = PlanType.HOTEL.getType();
                    tab.setText(text);
                    break;
                case 3:
                    text = PlanType.RESTAURANT.getType();
                    tab.setText(text);
                    break;
                default:
                    text = "Invalid Page";
                    tab.setText(text);
            }

        }).attach();

        binding.addPlan.setOnClickListener(v -> {
            int pos = binding.pager.getCurrentItem();
            switch (pos) {
                case 0:
                    startActivity(new Intent(HomeActivity.this, CarBudgetPlannerActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(HomeActivity.this, FlightPlannerActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(HomeActivity.this, CarBudgetPlannerActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(HomeActivity.this, CarBudgetPlannerActivity.class));
                    break;
            }
        });

    }

}
