package com.example.trip_plan_budget.activity.flight;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.model.AirportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class FlightActivity extends AppCompatActivity {
    private static final String TAG = "FlightActivity";
    private TextView fromIATA, fromCity;
    private TextView toIATA, toCity;
    private CardView fromCard, toCard;
    private TextView adult, child, infant;

    private DatabaseReference database;
    private ArrayList<AirportModel> airports;
    private AirportModel to, from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        initRoute();
        initOptions();

        initDatabase();
    }

    private void initDatabase() {
        airports = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("airports");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    airports.add(snapshot.getValue(AirportModel.class));
                }
                setDeparturePoint(airports.get(0));
                setLandingPoint(airports.get(1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setLandingPoint(AirportModel model) {
        to = model;
        toCity.setText(to.getPlaceName());
        toIATA.setText(to.getPlaceId().split("-")[0]);
    }

    private void setDeparturePoint(AirportModel model) {
        from = model;
        fromCity.setText(from.getPlaceName());
        fromIATA.setText(from.getPlaceId().split("-")[0]);
    }

    private void initRoute() {
        fromIATA = findViewById(R.id.from_iata);
        fromCity = findViewById(R.id.from_city);
        fromCard = findViewById(R.id.from_card);
        fromCard.setOnClickListener(view -> {
            if (airports != null) {
                new SimpleSearchDialogCompat(FlightActivity.this, "Search...",
                        "Select Airport?", null, airports,
                        (SearchResultListener<AirportModel>) (dialog, item, position) -> {
                            setDeparturePoint(item);
                            dialog.dismiss();
                        }).show();
            }
        });

        toIATA = findViewById(R.id.to_iata);
        toCity = findViewById(R.id.to_city);
        toCard = findViewById(R.id.to_card);
        toCard.setOnClickListener(view -> {
            if (airports != null) {
                new SimpleSearchDialogCompat(FlightActivity.this, "Search...",
                        "Select Airport?", null, airports,
                        (SearchResultListener<AirportModel>) (dialog, item, position) -> {
                            setLandingPoint(item);
                            dialog.dismiss();
                        }).show();
            }
        });

        findViewById(R.id.swap_iata).setOnClickListener(v -> {
            AirportModel temp = to;
            setDeparturePoint(from);
            setLandingPoint(temp);
        });
    }

    private void initOptions() {
        adult = findViewById(R.id.val_adult);
        child = findViewById(R.id.val_child);
        infant = findViewById(R.id.val_infant);

        findViewById(R.id.add_adult).setOnClickListener(view -> {
            int val = Integer.parseInt(adult.getText().toString());
            if (val >= 0) {
                ++val;
                adult.setText(val + "");
            }
        });

        findViewById(R.id.add_child).setOnClickListener(view -> {
            int val = Integer.parseInt(child.getText().toString());
            if (val >= 0) {
                ++val;
                child.setText(val + "");
            }
        });

        findViewById(R.id.add_infant).setOnClickListener(view -> {
            int val = Integer.parseInt(infant.getText().toString());
            if (val >= 0) {
                ++val;
                infant.setText(val + "");
            }
        });

        findViewById(R.id.remove_infant).setOnClickListener(view -> {
            int val = Integer.parseInt(infant.getText().toString());
            if (val > 0) {
                --val;
                infant.setText(val + "");
            }
        });

        findViewById(R.id.remove_child).setOnClickListener(view -> {
            int val = Integer.parseInt(child.getText().toString());
            if (val > 0) {
                --val;
                child.setText(val + "");
            }
        });

        findViewById(R.id.remove_adult).setOnClickListener(view -> {
            int val = Integer.parseInt(adult.getText().toString());
            if (val > 0) {
                --val;
                adult.setText(val + "");
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(view -> validate());
    }


    private void validate() {

    }
}
