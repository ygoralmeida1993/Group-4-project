package com.example.trip_plan_budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TransportationActivity extends AppCompatActivity {
    Button flight,car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        flight=findViewById(R.id.Flight);
        car=findViewById(R.id.Car);
    flight.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openFlightActivity();
        }

    });
    car.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCarActivity();
        }
    });
    }

    private void openCarActivity() {
        Intent intent=new Intent(this,ExplorerActivity.class);
        startActivity(intent);
    }

    private void openFlightActivity() {
        Intent intent=new Intent(this,ExplorerActivity.class);
        startActivity(intent);
    }

}
