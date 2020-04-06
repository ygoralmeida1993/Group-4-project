package com.example.trip_plan_budget.activity.places;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.car.FilterActivity;

public class ExplorerActivity extends AppCompatActivity {
    String budget;
    int approach;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        approach = bundle.getInt("approach");
        if (approach == 1) {
            budget = bundle.getString("budget");
        }

    }

    public void onClickBeaches(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 1);
        startActivity(intent);
    }

    public void onClickHillStation(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 2);
        startActivity(intent);
    }

    public void onClickRecreational(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 3);
        startActivity(intent);
    }

    public void onClickReligious(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 4);
        startActivity(intent);
    }

    public void onClickHiking(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 5);
        startActivity(intent);
    }

    public void onClickCultural(View view) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        intent.putExtra("budget", budget);
        intent.putExtra("approach", approach);
        intent.putExtra("placetype", 6);
        startActivity(intent);
    }
}