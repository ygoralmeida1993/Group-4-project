package com.example.trip_plan_budget.activity.places;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.databinding.ActivityNearbyPlacesBinding;
import com.example.trip_plan_budget.enumeration.EnumUtil;
import com.example.trip_plan_budget.enumeration.NearbyPlaceType;
import com.example.trip_plan_budget.model.places.Point;
import com.example.trip_plan_budget.service.NetworkService;

public class NearbyPlacesActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNearbyPlacesBinding binding;
    private String placeName;
    private Point point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNearbyPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        placeName = getIntent().getStringExtra("placeName");
        if (placeName != null) {
            NetworkService.getInstance().getPlacesPoint(placeName, point -> {
                if (point != null) {
                    this.point = point;

                    binding.nearbyAmusements.setOnClickListener(this);

                    binding.nearbyBeaches.setOnClickListener(this);

                    binding.nearbyHiking.setOnClickListener(this);

                    binding.nearbyReligious.setOnClickListener(this);

                    binding.nearbyHistorical.setOnClickListener(this);


                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NearbyPlacesActivity.this, NearbyPlacesListActivity.class);
        switch (v.getId()) {
            case R.id.nearbyAmusements:
                EnumUtil.serialize(NearbyPlaceType.AMUSEMENT).to(intent);
                break;
            case R.id.nearbyHiking:
                EnumUtil.serialize(NearbyPlaceType.HIKING).to(intent);
                break;
            case R.id.nearbyHistorical:
                EnumUtil.serialize(NearbyPlaceType.HISTORICAL).to(intent);
                break;
            case R.id.nearbyReligious:
                EnumUtil.serialize(NearbyPlaceType.RELIGIOUS).to(intent);
                break;
            case R.id.nearbyBeaches:
                EnumUtil.serialize(NearbyPlaceType.BEACHES).to(intent);
                break;
        }
        intent.putExtra("point", point);
        startActivity(intent);
    }
}
