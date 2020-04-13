package com.example.trip_plan_budget.activity.flight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.enumeration.FlightClass;
import com.example.trip_plan_budget.model.flight.AirportModel;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class FlightPlannerActivity extends AppCompatActivity {
    private static final String TAG = "FlightActivity";
    private TextView fromIATA, fromCity;
    private TextView toIATA, toCity;
    private CardView fromCard, toCard;
    private TextView adult, child, infant;
    private CheckBox roundTrip;

    private DatabaseReference database;
    private ArrayList<AirportModel> airports;
    private AirportModel to, from;

    private RadioButton[] radio;

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
        roundTrip = findViewById(R.id.round_trip);

        fromIATA = findViewById(R.id.from_iata);
        fromCity = findViewById(R.id.from_city);
        fromCard = findViewById(R.id.from_card);
        fromCard.setOnClickListener(view -> {
            if (airports != null) {
                new SimpleSearchDialogCompat(FlightPlannerActivity.this, null,
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
                new SimpleSearchDialogCompat(FlightPlannerActivity.this, null,
                        "Select Airport?", null, airports,
                        (SearchResultListener<AirportModel>) (dialog, item, position) -> {
                            setLandingPoint(item);
                            dialog.dismiss();
                        }).show();
            }
        });

        findViewById(R.id.swap_iata).setOnClickListener(v -> {
            AirportModel temp = from;
            setDeparturePoint(to);
            setLandingPoint(temp);
        });

        radio = new RadioButton[]{
                findViewById(R.id.class_business),
                findViewById(R.id.class_premium_eco),
                findViewById(R.id.class_first),
                findViewById(R.id.class_economy)};
        for (int i = 0; i < radio.length; i++) {
            final int temp = i;
            radio[i].setOnClickListener(v -> {
                for (int j = 0; j < radio.length; j++) {
                    if (temp == j) radio[j].setChecked(true);
                    else radio[j].setChecked(false);
                }
            });

        }
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
        FlightClass flightClass = FlightClass.FIRST_CLASS;
        for (RadioButton button : radio) {
            if (button.isChecked()) {
                switch (button.getId()) {
                    case R.id.class_business:
                        flightClass = FlightClass.BUSINESS;
                        break;
                    case R.id.class_premium_eco:
                        flightClass = FlightClass.PREMIUM_ECONOMY;
                        break;
                    case R.id.class_economy:
                        flightClass = FlightClass.ECONOMY;
                        break;
                    default:
                        flightClass = FlightClass.FIRST_CLASS;
                        break;
                }
                break;
            }
        }
        int a = Integer.parseInt(adult.getText().toString()),
                c = Integer.parseInt(child.getText().toString()),
                i = Integer.parseInt(infant.getText().toString());
        if ((from != null && to != null && to != from) && (a > 0 || c > 0)) {
            FlightModel model = new FlightModel(
                    from,
                    to,
                    roundTrip.isChecked(),
                    a, c, i,
                    flightClass);
            Intent intent = new Intent(FlightPlannerActivity.this, FlightDateActivity.class);
            intent.putExtra("flight", model);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(R.id.root_flight_activity), "Please Fill the fields properly.", Snackbar.LENGTH_LONG).show();
        }
    }
}
