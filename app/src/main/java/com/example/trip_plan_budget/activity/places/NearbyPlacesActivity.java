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
            NetworkService.getInstance().getPlacesPoint(
                    placeName, point -> {
                        if (point != null) {
                            this.point = point;

                            binding.nearbyAmusements.setOnClickListener(this);
                            binding.nearbyAmusementsImage.setOnClickListener(this);

                            binding.nearbyBeaches.setOnClickListener(this);
                            binding.nearbyBeachesImage.setOnClickListener(this);

                            binding.nearbyHiking.setOnClickListener(this);
                            binding.nearbyHikingImage.setOnClickListener(this);

                            binding.nearbyReligious.setOnClickListener(this);
                            binding.nearbyReligiousImage.setOnClickListener(this);

                            binding.nearbyHistorical.setOnClickListener(this);
                            binding.nearbyHistoricalImage.setOnClickListener(this);

                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NearbyPlacesActivity.this, NearbyPlacesListActivity.class);
        switch (v.getId()) {
            case R.id.nearbyAmusements:
            case R.id.nearbyAmusementsImage:
                EnumUtil.serialize(NearbyPlaceType.AMUSEMENT).to(intent);
                break;
            case R.id.nearbyHiking:
            case R.id.nearbyHikingImage:
                EnumUtil.serialize(NearbyPlaceType.HIKING).to(intent);
                break;
            case R.id.nearbyHistorical:
            case R.id.nearbyHistoricalImage:
                EnumUtil.serialize(NearbyPlaceType.HISTORICAL).to(intent);
                break;
            case R.id.nearbyReligious:
            case R.id.nearbyReligiousImage:
                EnumUtil.serialize(NearbyPlaceType.RELIGIOUS).to(intent);
                break;
            case R.id.nearbyBeaches:
            case R.id.nearbyBeachesImage:
                EnumUtil.serialize(NearbyPlaceType.BEACHES).to(intent);
                break;
        }
        intent.putExtra("point", point);
        startActivity(intent);
    }
}
