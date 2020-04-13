package com.example.trip_plan_budget.activity.places;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trip_plan_budget.adapter.NearbyPlaceAdapter;
import com.example.trip_plan_budget.databinding.ActivityNearbyPlacesListBinding;
import com.example.trip_plan_budget.enumeration.EnumUtil;
import com.example.trip_plan_budget.enumeration.NearbyPlaceType;
import com.example.trip_plan_budget.model.places.PlaceModel;
import com.example.trip_plan_budget.model.places.Point;
import com.example.trip_plan_budget.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

public class NearbyPlacesListActivity extends AppCompatActivity {
    private List<PlaceModel> places;
    private NearbyPlaceAdapter adapter;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress = new ProgressDialog(NearbyPlacesListActivity.this);
        showProgressDialog();
        com.example.trip_plan_budget.databinding.ActivityNearbyPlacesListBinding binding = ActivityNearbyPlacesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        places = new ArrayList<>();
        binding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(NearbyPlacesListActivity.this));
        adapter = new NearbyPlaceAdapter(places, place -> {
            Uri gmmIntentUri = Uri.parse("geo:" + place.getPoint().getLat() + "," + place.getPoint().getLon() + "?q=" + place.getPoint().getLat() + "," + place.getPoint().getLon() + "&z=14");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });
        binding.placesRecyclerView.setAdapter(adapter);

        NearbyPlaceType type = EnumUtil.deserialize(NearbyPlaceType.class).from(getIntent());
        Point point = getIntent().getParcelableExtra("point");
        if (type != null && point != null) {
            NetworkService.getInstance()
                    .getPlacesInRadius(
                            50000,
                            point.getLat(), point.getLon(),
                            type.getType(),
                            data -> {
                                runOnUiThread(() -> {

                                    mProgress.cancel();
                                    if (data != null) {
                                        this.places.clear();
                                        this.places.addAll(data);
                                        this.adapter.notifyDataSetChanged();
                                        if (places.size() == 0) {
                                            binding.placesRecyclerView.setVisibility(View.GONE);
                                            binding.emptyNearbyPlaces.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        binding.placesRecyclerView.setVisibility(View.GONE);
                                        binding.emptyNearbyPlaces.setVisibility(View.VISIBLE);
                                    }
                                });

                            });
        }
    }

    private void showProgressDialog() {
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();
    }

}
