package com.example.trip_plan_budget;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity implements LocationListener {
    private static final String url = "jdbc:mysql://192.168.0.12:3306/trip_plan";
    private static final String user = "root";
    private static final String pass = "";
    int approach, placetypeInt;
    String  placetype, cityName;
    double budget;
    private NumberPicker picker1;
    double approximateBudget=0;
    EditText  modeOfTransportation;
    AppCompatAutoCompleteTextView destination;
    EditText days, passangers;
    int passanger,day;
    Button calculate;
    TextView lat;
    LocationManager locationManager ;
    double currentLatitude;
    double currentLongitude;
    int budgetTotal=0;
    private String[] places = {"Scarborough","Toronto","Waterloo","Oshawa"};
    int budgetAverage=0;
    protected boolean gps_enabled, network_enabled;
    ArrayList<PlaceDetailsModel> placeDetailsModelArrayList;
    int people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        destination = (AppCompatAutoCompleteTextView) this.findViewById(R.id.destination);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, places);
        destination.setThreshold(1); //will start working from first character
        destination.setAdapter(adapter);
        // modeOfTransportation = (EditText) this.findViewById(R.id.modeTansport);
        days = (EditText) this.findViewById(R.id.noOfDays);
        picker1 = (NumberPicker) findViewById(R.id.numberpicker_main_picker);
        //passangers = (EditText) this.findViewById(R.id.noOfPassangers);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        // lat=(TextView) this.findViewById(R.id.lat);
        calculate=(Button)findViewById(R.id.calculate);
        picker1.setMaxValue(10);
        picker1.setMinValue(1);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        approach = bundle.getInt("approach");
        placetypeInt = bundle.getInt("placetype");
        if (placetypeInt == 1) {
            placetype = "Beaches";
        } else if (placetypeInt == 2) {
            placetype = "hillstation";
        } else if (placetypeInt == 3) {
            placetype = "recreational";
        } else if (placetypeInt == 4) {
            placetype = "religious";
        } else if (placetypeInt == 5) {
            placetype = "hiking";
        } else if (placetypeInt == 6) {
            placetype = "historical";
        }
        if (approach == 1) {
            budget = Double.parseDouble(bundle.getString("budget"));
            calculate.setText("Get Places");

        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                passanger = picker1.getValue();

            }
        });



    }
    private double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = dist * 180.0 / Math.PI;
        dist = dist * 60 * 1.1515*1000;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    public void CalculateBudget(View view) {
        cityName=destination.getText().toString().toLowerCase();

        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");
    }



    @Override
    public void onLocationChanged(Location location) {
        lat.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        Toast.makeText(getApplicationContext(),"inn",Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(FilterActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    private class ConnectMySql extends AsyncTask<String, Void, ArrayList<PlaceDetailsModel>> {
        String res = "";
        boolean success=false;
        //String place="Toronto";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList<PlaceDetailsModel> doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                String longitute,latitude,city,place,type,image = "";
                int budget=0;
                Statement st = con.createStatement();
                System.out.println(cityName+placetype);

                //ResultSet rs = st.executeQuery("select * from place_details where city='scarborough'and place_type='hiking'");
                ResultSet rs = st.executeQuery("select * from place_details where city='" + cityName + "'and place_type='" + placetype + "'");
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    longitute = rs.getString("longitude").toString() + "\n";
                    // int i = rs.getInt("userid");
                    latitude = rs.getString("latitude");
                    place = rs.getString("placename");
                    System.out.println("place" + place);
                    budget = Integer.parseInt(rs.getString("budget"));
                    type = rs.getString("place_type");
                    city = rs.getString("city");
                    image=rs.getString("images");
                    Log.d("test1",image);
                    Log.d("test2",type);
                    //Assuming you have a user object
                    PlaceDetailsModel placeDetailsModel = new PlaceDetailsModel(place, city, budget, longitute, latitude, type,image);
                    placeDetailsModelArrayList.add(placeDetailsModel);

                    //queery for getting places within budget
                }
                // System.out.println(longitute);
                //  System.out.println("Result"+placeDetailsModelArrayList);
                //res = result;
            }
            catch (Exception e) {
                e.printStackTrace();
                //res = e.toString();
            }
            return placeDetailsModelArrayList;
        }
        @Override
        protected void onPostExecute(ArrayList<PlaceDetailsModel> placeDetailsModelArrayList) {
            if (approach == 0) {
                Log.d("passanger", String.valueOf(passanger));
                System.out.println(placeDetailsModelArrayList.size() + "size");
                for (PlaceDetailsModel e : placeDetailsModelArrayList) {
                    budgetTotal += e.getBudget();
                }
                budgetAverage = budgetTotal / (placeDetailsModelArrayList.size());
                passanger = picker1.getValue();
                day = Integer.parseInt(days.getText().toString());
                Location selected_location = new Location("locationA");
                selected_location.setLatitude(currentLatitude);
                selected_location.setLongitude(currentLongitude);
                System.out.println("currentLatitude" + currentLatitude + currentLongitude);
                Location near_locations = new Location("locationB");
                near_locations.setLatitude(Double.parseDouble(placeDetailsModelArrayList.get(0).getLatitude()));
                near_locations.setLongitude(Double.parseDouble(placeDetailsModelArrayList.get(0).getLongitude()));
                System.out.println("bluffers Latitude" + Double.parseDouble(placeDetailsModelArrayList.get(0).getLatitude()) + Double.parseDouble(placeDetailsModelArrayList.get(0).getLongitude()));
                double distance = (selected_location.distanceTo(near_locations)) * 0.00062137;
                System.out.println("distance" + distance);
                approximateBudget = (distance * 0.30) + (budgetAverage * passanger * day);
                System.out.println(approximateBudget + "approximateBudget");
                placeDetailsModelArrayList.removeAll(placeDetailsModelArrayList);
                budgetTotal = 0;
                Intent intent = new Intent(getApplicationContext(), WithoutBudget.class);
                intent.putExtra("approximateBudget", approximateBudget);
                startActivity(intent);
            }
            else{
                passanger = picker1.getValue();
                // passanger = Integer.parseInt(passangers.getText().toString());
                day = Integer.parseInt(days.getText().toString());
                for (int i = 0; i < placeDetailsModelArrayList.size(); i++) {
                    double finalBudget=0;
                    Log.d("placeDetailsModelList", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
                    finalBudget=placeDetailsModelArrayList.get(i).getBudget();
                    finalBudget=finalBudget*day*passanger;
                    Log.d("finalBudget before", String.valueOf(finalBudget));
                    Location selected_location = new Location("locationA");
                    selected_location.setLatitude(currentLatitude);
                    selected_location.setLongitude(currentLongitude);
                    System.out.println("currentLatitude" + currentLatitude + currentLongitude);
                    Location near_locations = new Location("locationB");
                    near_locations.setLatitude(Double.parseDouble(placeDetailsModelArrayList.get(i).getLatitude()));
                    near_locations.setLongitude(Double.parseDouble(placeDetailsModelArrayList.get(i).getLongitude()));
                    double distance = (selected_location.distanceTo(near_locations)) * 0.00062137;
                    Log.d("distance", String.valueOf(distance));
                    System.out.println(distance + "distance");
                    finalBudget=finalBudget+(distance*0.30);
                    Log.d("finalBudget after", String.valueOf(finalBudget));
                    placeDetailsModelArrayList.get(i).setBudget((int) finalBudget);
                    Log.d("ModelList", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
                }
                ArrayList<PlaceDetailsModel> l1 = new ArrayList<PlaceDetailsModel>();
                for (int i = 0; i <placeDetailsModelArrayList.size(); i++) {
                    Log.d("value", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
                    Log.d("budget", String.valueOf(budget));
                    if((placeDetailsModelArrayList.get(i).getBudget())<budget){

                        l1.add(placeDetailsModelArrayList.get(i));}
                }
                placeDetailsModelArrayList.removeAll(placeDetailsModelArrayList);
                Intent intent = new Intent(getApplicationContext(), WithBudget.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("placeDetailsModelArrayList", l1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

}
