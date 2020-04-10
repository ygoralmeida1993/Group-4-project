package com.example.trip_plan_budget.activity.flight;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.adapter.FlightPriceListAdapter;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.example.trip_plan_budget.model.flight.FlightPriceListModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PriceListActivity extends AppCompatActivity {
    private static final String TAG = "PriceListActivity";
    private FlightModel model;

    private RecyclerView recyclerView;
    private ProgressDialog mProgress;

    private FlightPriceListModel flightPriceListModel;
    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        showProgressDialog();

        recyclerView = findViewById(R.id.flight_price_recycler);
        emptyList = findViewById(R.id.flight_price_empty);

        model = getIntent().getParcelableExtra("flight");
        mProgress = new ProgressDialog(PriceListActivity.this);
        if (model != null) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    URL gasApi = null;
                    try {
                        gasApi = new URL(getString(R.string.url_browse_routes) + model.getDeparture().getPlaceId() + "/" + model.getLanding().getPlaceId() + "/" + model.getFrom());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpsURLConnection myConnection =
                                (HttpsURLConnection) gasApi.openConnection();
                        myConnection.setRequestMethod("GET");
                        myConnection.setRequestProperty("x-rapidapi-host",
                                "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
                        myConnection.setRequestProperty("x-rapidapi-key",
                                "b516667e8emshc62d0a04217dcf1p1d99d6jsnf232f2210a0d");
                        myConnection.setRequestProperty("content-type",
                                "application/json");
                        Log.d("getResponseCode", "" + myConnection.getResponseCode());
                        if (myConnection.getResponseCode() == 200) {
                            Log.d("getResponseCode", "getResponseCode");
                            InputStream responseBody = myConnection.getInputStream();
                            String jsonString = convertStreamToString(responseBody);
                            Log.v(TAG, jsonString);
                            flightPriceListModel = new Gson().fromJson(jsonString, FlightPriceListModel.class);
                            myConnection.disconnect();
                            responseBody.close();
                        } else {

                            Log.d("value error", "error");
                        }
                    } catch (Exception e) {
                        mProgress.cancel();
                        recyclerView.setVisibility(View.GONE);
                        emptyList.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    } finally {
                        if (flightPriceListModel != null) generateList();
                    }
                }


            });
        }
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void generateList() {
        FlightPriceListAdapter adapter = new FlightPriceListAdapter(flightPriceListModel.getPrices());
        recyclerView.setLayoutManager(new LinearLayoutManager(PriceListActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

    }

    private void showProgressDialog() {
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Flight Options...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();
    }
}
