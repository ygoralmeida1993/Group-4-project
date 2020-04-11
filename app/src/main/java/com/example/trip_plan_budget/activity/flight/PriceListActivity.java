package com.example.trip_plan_budget.activity.flight;

import android.app.AlertDialog;
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
import com.example.trip_plan_budget.interfaces.OnFlightPriceListClickListener;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.example.trip_plan_budget.model.flight.FlightPriceListModel;
import com.example.trip_plan_budget.model.flight.PriceModel;
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
    private TextView errorList;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        recyclerView = findViewById(R.id.flight_price_recycler);
        emptyList = findViewById(R.id.flight_price_empty);
        errorList = findViewById(R.id.flight_price_error);

        dialog = new AlertDialog.Builder(PriceListActivity.this);
        dialog.setTitle("Are your Sure?");
        dialog.setMessage("Please confirm your selection");
        dialog.setNegativeButton("Cancel", (d, i) -> {
            d.cancel();
        });


        model = getIntent().getParcelableExtra("flight");
        mProgress = new ProgressDialog(PriceListActivity.this);
        showProgressDialog();
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
                                getString(R.string.skyscanner_api_key));
                        myConnection.setRequestProperty("content-type",
                                "application/json");
                        Log.d("getResponseCode", "" + myConnection.getResponseCode());
                        InputStream responseBody = myConnection.getInputStream();
                        String jsonString = convertStreamToString(responseBody);
                        Log.v(TAG, jsonString);
                        if (myConnection.getResponseCode() == 200) {
                            Log.d("getResponseCode", "getResponseCode");
                            flightPriceListModel = new Gson().fromJson(jsonString, FlightPriceListModel.class);

                        } else {
                            runOnUiThread(() -> {
                                mProgress.cancel();
                                recyclerView.setVisibility(View.GONE);
                                errorList.setVisibility(View.VISIBLE);
                                Log.d("value error", "error");
                            });
                            myConnection.disconnect();
                            responseBody.close();
                        }
                        runOnUiThread(() -> {
                            if (flightPriceListModel != null)
                                if (flightPriceListModel.getPrices().size() > 0) generateList();
                                else {
                                    mProgress.cancel();
                                    recyclerView.setVisibility(View.GONE);
                                    emptyList.setVisibility(View.VISIBLE);
                                }
                        });

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }


            });
        }
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
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
        FlightPriceListAdapter adapter = new FlightPriceListAdapter(PriceListActivity.this, flightPriceListModel.getPrices(), flightPriceListModel.getCarriers(), new OnFlightPriceListClickListener() {
            @Override
            public void onCLick(PriceModel priceModel) {
                dialog.setPositiveButton("Confirm", (d, i) -> {

                });
                dialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(PriceListActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        mProgress.cancel();

    }

    private void showProgressDialog() {
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Flight Options...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();
    }


}
