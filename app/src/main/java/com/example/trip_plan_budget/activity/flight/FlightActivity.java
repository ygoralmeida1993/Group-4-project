package com.example.trip_plan_budget.activity.flight;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trip_plan_budget.R;

public class FlightActivity extends AppCompatActivity {
    TextView fromIATA, fromCity;
    TextView toIATA, toCity;
    CardView from, to;
    ImageView swapIATA;

    CheckBox roundTrip;

    TextView adult, child, infant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        initRoute();
        initOptions();
        initMisc();

    }


    private void initRoute() {
        fromIATA = findViewById(R.id.from_iata);
        fromCity = findViewById(R.id.from_city);
        from = findViewById(R.id.from_card);

        toIATA = findViewById(R.id.to_iata);
        toCity = findViewById(R.id.to_city);
        to = findViewById(R.id.to_card);

        findViewById(R.id.swap_iata).setOnClickListener(v -> {

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
    }

    private void initMisc() {
        findViewById(R.id.btn_next).setOnClickListener(view -> validate());
    }

    private void validate() {

    }
}
