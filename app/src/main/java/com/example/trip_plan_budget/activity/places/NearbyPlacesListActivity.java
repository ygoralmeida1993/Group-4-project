package com.example.trip_plan_budget.activity.places;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
    private ActivityNearbyPlacesListBinding binding;
    private NearbyPlaceType type;
    private Point point;
    private List<PlaceModel> places;
    private NearbyPlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNearbyPlacesListBinding.inflate(getLayoutInflater());

        places = new ArrayList<>();
        binding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(NearbyPlacesListActivity.this));
        adapter = new NearbyPlaceAdapter(places, place -> {
            Uri gmmIntentUri = Uri.parse("geo:" + place.getPoint().getLat() + "," + place.getPoint().getLon() + "?z=16");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        type = EnumUtil.deserialize(NearbyPlaceType.class).from(getIntent());
        point = getIntent().getParcelableExtra("point");
        if (type != null && point != null) {
            NetworkService.getInstance()
                    .getPlacesInRadius(
                            5000,
                            point.getLat(), point.getLon(),
                            type.getType(),
                            places -> {

                                if (places != null) {
                                    this.places.clear();
                                    this.places.addAll(places);
                                    adapter.notifyDataSetChanged();
                                }

                            });
        }
    }

}
