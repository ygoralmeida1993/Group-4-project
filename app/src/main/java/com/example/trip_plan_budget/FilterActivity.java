package com.example.trip_plan_budget;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;

import android.text.InputType;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import android.widget.DatePicker;

public class FilterActivity extends AppCompatActivity implements LocationListener {
    private static final String url = "jdbc:mysql://192.168.0.12:3306/trip_plan";
    private static final String user = "root";
    private static final String pass = "";
    ImageButton inc;
    ImageButton    dec;//for icrement decrement passangers
    int counter=1;
    int approach, placetypeInt;
    String  placetype, cityName;
    double budget;
    private NumberPicker picker1;
    double approximateBudget=0;
    EditText days;
    int passanger,day;
    TextView no_passanger;
    Button calculate;
    TextView lat;
    LocationManager locationManager ;
    double currentLatitude;
    double currentLongitude;
    AutoCompleteTextView destination;
    int budgetTotal=0;
    DatePickerDialog picker;
    DatabaseReference databasePlaces;
    private String[] places = {"Scarborough","Toronto","Waterloo","Oshawa"};
    int budgetAverage=0;
    protected boolean gps_enabled, network_enabled;
    ArrayList<PlaceDetailsModel> placeDetailsModelArrayList;
    Button fromDate,toDate;
    ArrayList<GasApiModel> gasApiModelArrayList;
    ArrayList<WeatherApiModel> weatherApiModelArrayList;
    int people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        fromDate = findViewById(R.id.btnDate);
        counter = 1;
        no_passanger=findViewById(R.id.passanger);
no_passanger.setText("1");
        inc = (ImageButton) findViewById(R.id.add);
        dec = (ImageButton) findViewById(R.id.minus);

        toDate = findViewById(R.id.btnDateTo);
        TextView date = findViewById(R.id.tvSelectedDate);
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                            }
                        }, 0, 0, 0);

                datePickerDialog = new DatePickerDialog(FilterActivity.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                toDate.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                            }
                        }, 0, 0, 0);

                datePickerDialog = new DatePickerDialog(FilterActivity.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                fromDate.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        gasApiModelArrayList=new ArrayList<>();
        String[] places= { "Scarborough", "Toronto", "Brampton", "North York", "Ottawa", "Hamilton",
                "London", "Windsor", "Mississauga", "Kitchner", "Markham", "Waterloo" };
        destination = (AutoCompleteTextView) findViewById(R.id.destination);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, places);
        destination.setThreshold(1);
        destination.setAdapter(arrayAdapter);
        //implementation for gas api
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL gasApi = null;
                try {
                    gasApi = new URL("https://api.collectapi.com/gasPrice/canada");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpsURLConnection myConnection =
                            (HttpsURLConnection) gasApi.openConnection();
                    myConnection.setRequestProperty("Authorization",
                            "apikey 5jpE9jkydP6DsPLJgVRuPf:4dRQkQrqaWwgIAq9M2FNhE");
                    if (myConnection.getResponseCode() == 200) {

                        InputStream responseBody = myConnection.getInputStream();
                        String jsonString=convertStreamToString(responseBody);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(jsonString);
                            JSONArray array = json.getJSONArray("result");
                            for(int m=0;m<array.length();m++){
                                JSONObject place = array.getJSONObject(m);
                                String province = place.getString("name");
                                String currency = place.getString("currency");
                                String gasoline = place.getString("gasoline");
                                GasApiModel response=new GasApiModel(province,gasoline,currency);
                                gasApiModelArrayList.add(response);
                            }
                            Log.d("gasApiModelArrayList",gasApiModelArrayList+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myConnection.disconnect();
                    } else {

                        Log.d("value error","");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
//weather api
        weatherApiModelArrayList=new ArrayList<>();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL weatherApi = null;
                try {
                    weatherApi = new URL("https://api.darksky.net/forecast/13e04b25432c5d707a0a3c080d15a5b7/43.642567,-79.387054");
                } catch (MalformedURLException e) {
                    Log.d("weatherApi", "error");
                    e.printStackTrace();
                }
                try {
                    Log.d("weatherApi", "try");
                    HttpsURLConnection myConnection =
                            (HttpsURLConnection) weatherApi.openConnection();
                    myConnection.setRequestProperty("x-rapidapi-host",
                            "weather2020-weather-v1.p.rapidapi.com");
                    myConnection.setRequestProperty("x-rapidapi-key",
                            "6d0b756940msh47651648d142f8fp126561jsn497d176a16e6");
                    Log.d("weatherApi", String.valueOf(myConnection.getResponseCode()));
                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody = myConnection.getInputStream();
                        String jsonString=convertStreamToString(responseBody);
                        Log.d("jsonstring", String.valueOf(jsonString));

                        JSONObject json = null;
                        try {
                            JSONObject mainObject = new JSONObject(jsonString);
                            JSONObject dailyObject = mainObject.getJSONObject("daily");
                            JSONArray weatherArray = dailyObject.getJSONArray("data");
                            Log.d("daily array", String.valueOf(weatherArray));
                            for(int m=0;m<weatherArray.length();m++){
                                JSONObject day = weatherArray.getJSONObject(m);
                                String icon = day.getString("icon");
                                Double temperatureMax = Double.valueOf(day.getString("temperatureMax"));
                                Double temperatureMin = Double.valueOf(day.getString("temperatureMin"));
                                Double windSpeed = Double.valueOf(day.getString("windSpeed"));
                                WeatherApiModel response=new WeatherApiModel(icon,temperatureMax,temperatureMin,windSpeed);
                                weatherApiModelArrayList.add(response);
                            }
                            Log.d("weatherApiModelArray",weatherApiModelArrayList+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myConnection.disconnect();
                    } else {
                        Log.d("value error","");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });

        databasePlaces= FirebaseDatabase.getInstance().getReference("places");

      //  days = (EditText) this.findViewById(R.id.noOfDays);
       // picker1 = (NumberPicker) findViewById(R.id.numberpicker_main_picker);
        //passangers = (EditText) this.findViewById(R.id.noOfPassangers);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        // lat=(TextView) this.findViewById(R.id.lat);
        calculate=(Button)findViewById(R.id.calculate);
     //   picker1.setMaxValue(10);
      //  picker1.setMinValue(1);
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
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
//        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                passanger = picker1.getValue();
//
//            }
//        });
    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
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
    @Override
    protected void onStart() {
        super.onStart();
        databasePlaces.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                placeDetailsModelArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PlaceDetailsModel places = postSnapshot.getValue(PlaceDetailsModel.class);
                    placeDetailsModelArrayList.add(places);
                }
                Log.d("places from database", String.valueOf(placeDetailsModelArrayList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    //main method for all calculations
    public void CalculateBudget(View view) {
        ArrayList<PlaceDetailsModel> placeDetailsList=new ArrayList<>();
        if (destination.getText().toString().isEmpty()) {
            destination.setError("Please enter destination");
            return;
        }
        if (fromDate.getText().toString().isEmpty()) {
            fromDate.setError("Select date");
            return;
        }
        if (toDate.getText().toString().isEmpty()) {
            toDate.setError("Select date");
            return;
        }
        cityName=destination.getText().toString().toLowerCase();
//new code with firebase
       // passanger = picker1.getValue();//for passanger
        passanger=2;

        String city=cityName.substring(0, 1).toUpperCase()+ cityName.substring(1);
        String placeType=placetype.substring(0, 1).toUpperCase()+ placetype.substring(1);
        Log.d("data",city+placeType+"   "+placeDetailsModelArrayList.get(0).getCity());
        for (int i = 0; i < placeDetailsModelArrayList.size(); i++){
            if(placeDetailsModelArrayList.get(i).getCity().equals(city)&&
                    placeDetailsModelArrayList.get(i).getPlace_type().equals( placeType)){
                Log.d("inside","inside");
            placeDetailsList.add(placeDetailsModelArrayList.get(i));
                Log.d("inside", String.valueOf(placeDetailsList));
            }
        }
       // placeDetailsModelArrayList.removeAll(placeDetailsModelArrayList);
        placeDetailsModelArrayList.addAll(placeDetailsList);
        Collections.copy(placeDetailsModelArrayList, placeDetailsList);
        Log.d("places after filtering", String.valueOf(placeDetailsModelArrayList));
        // passanger = Integer.parseInt(passangers.getText().toString());
      //  day = Integer.parseInt(days.getText().toString());//for days
        day=2;
        for (int i = 0; i < placeDetailsModelArrayList.size(); i++)
        {
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
        Intent intent = new Intent(getApplicationContext(), CarDetailsActivity.class);
        Bundle bundle = new Bundle();
        Log.d("places before intent", String.valueOf(l1));
        bundle.putParcelableArrayList("placeDetailsModelArrayList", l1);
        intent.putExtras(bundle);
        startActivity(intent);

       // ConnectMySql connectMySql = new ConnectMySql();
        //connectMySql.execute("");

    }
    public void onClickIncDec(View v)
    {
        boolean showText = false;

    switch (v.getId()) {
        case R.id.add:
            if(counter<7){
            counter++;}
            showText = true;
            break;
        case R.id.minus:
            if(counter>1){
            counter--;}
            showText = true;
            break;


}
        if(showText)
            no_passanger.setText(""+ counter);
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
                    //PlaceDetailsModel placeDetailsModel = new PlaceDetailsModel(place, city, budget, longitute, latitude, type,image);
                   //placeDetailsModelArrayList.add(placeDetailsModel);

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
               // day = Integer.parseInt(days.getText().toString());
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
