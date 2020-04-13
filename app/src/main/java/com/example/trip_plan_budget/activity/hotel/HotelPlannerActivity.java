package com.example.trip_plan_budget.activity.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.adapter.PlaceSearchAdapter;
import com.example.trip_plan_budget.misc.Utils;
import com.example.trip_plan_budget.service.NetworkService;
import com.ferfalk.simplesearchview.SimpleSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HotelPlannerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> places, holder;
    private PlaceSearchAdapter adapter;
    private SimpleSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        places = new ArrayList<>();
        places.addAll(Arrays.asList(Utils.getCities()));
        holder = new ArrayList<>(places);
        recyclerView = findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(HotelPlannerActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new PlaceSearchAdapter(holder, place -> {
            NetworkService.getInstance().getPlacesPoint(
                    place, point -> {
                        Intent intent = new Intent(HotelPlannerActivity.this, HotelDetailsActivity.class);
                        intent.putExtra("point", point);
                        startActivity(intent);
                    });
        });
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                if (!query.isEmpty()) {
                    List<String> temp = places.stream().filter(place ->
                            place.replaceAll("\\s", "").toLowerCase()
                                    .contains(query.replaceAll("\\s", "").toLowerCase()))
                            .collect(Collectors.toList());
                    holder.clear();
                    holder.addAll(temp);
                } else {
                    holder.clear();
                    holder.addAll(places);
                }
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                if (!newText.isEmpty()) {
                    List<String> temp = places.stream().filter(place ->
                            place.replaceAll("\\s", "").toLowerCase()
                                    .contains(newText.replaceAll("\\s", "").toLowerCase()))
                            .collect(Collectors.toList());
                    holder.clear();
                    holder.addAll(temp);
                } else {
                    holder.clear();
                    holder.addAll(places);
                }
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        if (searchView == null)
            searchView = findViewById(R.id.searchView);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (searchView.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
