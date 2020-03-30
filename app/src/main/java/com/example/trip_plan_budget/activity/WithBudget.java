package com.example.trip_plan_budget.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.adapter.PlaceAdapter;
import com.example.trip_plan_budget.model.PlaceDetailsModel;

import java.util.ArrayList;
import java.util.Objects;

public class WithBudget extends AppCompatActivity {
    ArrayList<PlaceDetailsModel> placeDetailsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_budget);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        Bundle bundle = getIntent().getExtras();
        //placeDetailsModelArrayList = (ArrayList<PlaceDetailsModel>) bundle.getSerializable("placeDetailsModelArrayList");
        placeDetailsModelArrayList = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("placeDetailsModelArrayList");
        Log.d("testing", String.valueOf(placeDetailsModelArrayList.get(0).getBudget()));
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        PlaceAdapter adapter = new PlaceAdapter(placeDetailsModelArrayList);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}