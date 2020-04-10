package com.example.trip_plan_budget.activity.flight;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.model.flight.FlightModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        model = getIntent().getParcelableExtra("flight");

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
//                            JSONObject json = null;
//                            try {
//                                json = new JSONObject(jsonString);
//                                Log.d("gasApijsonString", jsonString + "");
//                                JSONArray array = json.getJSONArray("result");
//                                for (int m = 0; m < array.length(); m++) {
//                                    JSONObject place = array.getJSONObject(m);
//                                    String province = place.getString("name");
//                                    String currency = place.getString("currency");
//                                    String gasoline = place.getString("gasoline");
//                                    GasApiModel response = new GasApiModel(province, gasoline, currency);
//                                    gasApiModelArrayList.add(response);
//                                }
//                                Log.d("gasApiModelArrayList", gasApiModelArrayList + "");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            myConnection.disconnect();
                            responseBody.close();
                        } else {

                            Log.d("value error", "error");
                        }
                    } catch (IOException e) {
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
}
