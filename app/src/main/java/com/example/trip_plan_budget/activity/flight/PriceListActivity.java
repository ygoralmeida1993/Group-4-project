package com.example.trip_plan_budget.activity.flight;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.HomeActivity;
import com.example.trip_plan_budget.adapter.FlightPriceListAdapter;
import com.example.trip_plan_budget.databinding.ActivityPriceListBinding;
import com.example.trip_plan_budget.interfaces.callback.OnFlightPriceListClickListener;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.example.trip_plan_budget.model.flight.FlightPriceListModel;
import com.example.trip_plan_budget.model.flight.PriceModel;
import com.example.trip_plan_budget.service.DatabaseService;
import com.google.android.material.snackbar.Snackbar;
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
    private ActivityPriceListBinding binding;
    private FlightModel model;
    private ProgressDialog mProgress;
    private FlightPriceListModel flightPriceListModel;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPriceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            AsyncTask.execute(() -> {
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
                            binding.flightPriceRecycler.setVisibility(View.GONE);
                            binding.flightPriceError.setVisibility(View.VISIBLE);
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
                                binding.flightPriceRecycler.setVisibility(View.GONE);
                                binding.flightPriceEmpty.setVisibility(View.VISIBLE);
                            }
                    });

                } catch (Exception e) {

                    e.printStackTrace();
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
                    model.setCarriers(flightPriceListModel.getCarriers(priceModel));
                    model.setPriceModel(priceModel);
                    DatabaseService.getInstance().addFlightPlan(model, result -> {
                        Snackbar.make(binding.getRoot(),
                                result ? "Added new Flight Plan" : "Failed to add Flight Plan",
                                Snackbar.LENGTH_LONG).show();
                        if (result) {
                            Intent intent = new Intent(PriceListActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                });
                dialog.show();
            }
        });
        binding.flightPriceRecycler.setLayoutManager(new LinearLayoutManager(PriceListActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.flightPriceRecycler.setAdapter(adapter);
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
