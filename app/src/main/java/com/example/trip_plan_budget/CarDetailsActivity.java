package com.example.trip_plan_budget;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarDetailsActivity extends AppCompatActivity {
    private RadioGroup carType;
    private RadioGroup carBrand;
    private Spinner carMake;
    private Animation animationUp;
    private Animation animationDown;
    ArrayList<PlaceDetailsModel> placeDetailsModelArrayList;
    ArrayList<CarMileageModel> carMileageModelArrayList;
    ArrayList<WeatherApiModel> weatherApiModelArrayList;
    RadioButton carTypeRadioButton, carBrandRadioButton;
    DatabaseReference databaseMileage;
    double originalCarMileage;
    double currentLatitude;

    double currentLongitude;
    double newCarMileage;
    String passanger, toDate, fromDate, currentGasPrice;
    Double budget;
    int totalDays = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        carType = findViewById(R.id.carType);
        databaseMileage = FirebaseDatabase.getInstance().getReference("mileage");
        carBrand = findViewById(R.id.CarBrands);
        TextView txtTitle = (TextView) findViewById(R.id.content_text);
        TextView carBrands = (TextView) findViewById(R.id.CarBrand);
        TextView CarMakes = (TextView) findViewById(R.id.CarMakes);
        carType.setVisibility(View.GONE);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        weatherApiModelArrayList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        //placeDetailsModelArrayList = (ArrayList<PlaceDetailsModel>) bundle.getSerializable("placeDetailsModelArrayList");
        placeDetailsModelArrayList = this.getIntent().getExtras().getParcelableArrayList("placeDetailsModelArrayList");
        weatherApiModelArrayList = this.getIntent().getExtras().getParcelableArrayList("weatherApiModelArrayList");
        passanger = getIntent().getExtras().getString("passenger");
        toDate = getIntent().getExtras().getString("toDate");
        budget = getIntent().getExtras().getDouble("budget");
        fromDate = getIntent().getExtras().getString("fromDate");
        toDate = toDate.substring(0, 2);
        fromDate = fromDate.substring(0, 2);
        totalDays = (Integer.parseInt(toDate) - Integer.parseInt(fromDate));
        totalDays += 1;
        currentGasPrice = getIntent().getExtras().getString("currentGasPrice");
        Log.d("All passed data", "" + passanger + "" + toDate + "" + fromDate + "" + currentGasPrice);
        Log.d("totalDays", "" + currentGasPrice);
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        carMake = (Spinner) findViewById(R.id.CarMake);

        List<String> list = new ArrayList<String>();
        list.add("2020");
        list.add("2019");
        list.add("2018");
        list.add("2017");
        list.add("2016");
        list.add("2015");
        list.add("2014");
        list.add("2013");
        list.add("2012");
        list.add("2011");
        list.add("2010");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMake.setAdapter(dataAdapter);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carBrand.setVisibility(View.GONE);
                carBrand.startAnimation(animationUp);
                carMake.setVisibility(View.GONE);
                carMake.startAnimation(animationUp);
                if (carType.isShown()) {
                    carType.setVisibility(View.GONE);
                    carType.startAnimation(animationUp);
                } else {
                    carBrand.setVisibility(View.GONE);
                    carMake.setVisibility(View.GONE);
                    carType.setVisibility(View.VISIBLE);
                    carType.startAnimation(animationDown);
                }
            }
        });
        carBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carType.setVisibility(View.GONE);
                carType.startAnimation(animationUp);
                carMake.setVisibility(View.GONE);
                carMake.startAnimation(animationUp);
                if (carBrand.isShown()) {
                    carBrand.setVisibility(View.GONE);
                    carBrand.startAnimation(animationUp);
                } else {
                    carMake.setVisibility(View.GONE);
                    carType.setVisibility(View.GONE);
                    carBrand.setVisibility(View.VISIBLE);
                    carBrand.startAnimation(animationDown);
                }
            }
        });
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
        CarMakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carBrand.setVisibility(View.GONE);
                carBrand.startAnimation(animationUp);
                carType.setVisibility(View.GONE);
                carType.startAnimation(animationUp);
                if(carMake.isShown()){
                    carMake.setVisibility(View.GONE);
                    carMake.startAnimation(animationUp);
                }
                else{
                    carType.setVisibility(View.GONE);
                    carMake.setVisibility(View.VISIBLE);
                    carMake.startAnimation(animationDown);
                }
            }
        });
    }
    public void CalculateBudget(View view) {
        int carTypeSelectedId = carType.getCheckedRadioButtonId();
        carTypeRadioButton = (RadioButton) findViewById(carTypeSelectedId);
         String selectedCarType= String.valueOf(carTypeRadioButton.getText()).toLowerCase();//car type
        int carBrandSelectedId = carBrand.getCheckedRadioButtonId();
        carBrandRadioButton = (RadioButton) findViewById(carBrandSelectedId);
        String selectedCarBrand= String.valueOf(carBrandRadioButton.getText()).toLowerCase();//car brand
        String carMakeSelectedId = carMake.getSelectedItem().toString();//car make
        Log.d("car details",""+selectedCarType+selectedCarBrand+carMakeSelectedId);
        for(int i=0;i<carMileageModelArrayList.size();i++){
            Log.d("currentTemp", String.valueOf(weatherApiModelArrayList.size()));
            if(carMileageModelArrayList.get(i).getCarBrand().equals(selectedCarBrand)&&carMileageModelArrayList.get(i).getCarMake().equals(carMakeSelectedId)&&
            carMileageModelArrayList.get(i).getCarType().equals(selectedCarType)){
                originalCarMileage=carMileageModelArrayList.get(i).getMileage();
            }
        }
        //implementing algorithm
        //for from date
        double currentTemp=weatherApiModelArrayList.get(0).getTempMax();

        Log.d("currentTemp", String.valueOf(weatherApiModelArrayList.get(0).getTempMax()));
        double differenceTemp=30-currentTemp;

        if(currentTemp<=30&&currentTemp>=20){
            newCarMileage=originalCarMileage;
        }
        else if(differenceTemp<=7){
            if(weatherApiModelArrayList.get(0).getIcon().equals("snow")){
                newCarMileage=originalCarMileage*0.93;
            }
            else{
                newCarMileage=originalCarMileage*0.95;
            }
        }
        else if(8<=differenceTemp||differenceTemp<15){
            if(weatherApiModelArrayList.get(0).getIcon().equals("snow")){
                newCarMileage=originalCarMileage*0.73;
            }
            else{
                newCarMileage=originalCarMileage*0.75;
            }
        }

        else if(16<=differenceTemp||differenceTemp<30){
            if(weatherApiModelArrayList.get(0).getIcon().equals("snow")){
                newCarMileage=originalCarMileage*0.53;
            }
            else{
                newCarMileage=originalCarMileage*0.55;
            }
        }

        else if(31<=differenceTemp||differenceTemp<37){
            if(weatherApiModelArrayList.get(0).getIcon().equals("snow")){
                newCarMileage=originalCarMileage*0.33;
            }
            else{
                newCarMileage=originalCarMileage*0.35;
            }
        }

        Log.d("carMileage", String.valueOf(newCarMileage));
        Log.d("carMileage", String.valueOf(originalCarMileage));
        for (int i = 0; i < placeDetailsModelArrayList.size(); i++)
        {
            double finalBudget=0;
            Log.d("placeDetailsModelList", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
            finalBudget=placeDetailsModelArrayList.get(i).getBudget();
            //   finalBudget=finalBudget*day*passanger;
            Log.d("finalBudget before", String.valueOf(finalBudget));
            Location selected_location = new Location("locationA");
            selected_location.setLatitude(currentLatitude);
            selected_location.setLongitude(currentLongitude);
            System.out.println("currentLatitude"+currentLatitude+currentLongitude);
            Location near_locations = new Location("locationB");
            near_locations.setLatitude(Double.parseDouble(placeDetailsModelArrayList.get(i).getLatitude()));
            near_locations.setLongitude(Double.parseDouble(placeDetailsModelArrayList.get(i).getLongitude()));
            double distance = (selected_location.distanceTo(near_locations)) * 0.00062137;
            Log.d("distance", String.valueOf(distance));
            System.out.println(distance + "distance");
            finalBudget=finalBudget+(distance*newCarMileage);
            finalBudget=finalBudget*Double.parseDouble(currentGasPrice)*1.25;
            Log.d("finalBudget after", String.valueOf(finalBudget));
            placeDetailsModelArrayList.get(i).setBudget((int) finalBudget);
            Log.d("ModelList", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
        }
        ArrayList<PlaceDetailsModel> l1 = new ArrayList<PlaceDetailsModel>();
        for (int i = 0; i <placeDetailsModelArrayList.size(); i++) {
            Log.d("value", String.valueOf(placeDetailsModelArrayList.get(i).getBudget()));
            Log.d("budget", String.valueOf(budget));
           if((placeDetailsModelArrayList.get(i).getBudget())< budget){

                l1.add(placeDetailsModelArrayList.get(i));}
        }
        Intent intent = new Intent(getApplicationContext(), WithBudget.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("placeDetailsModelArrayList", placeDetailsModelArrayList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        carMileageModelArrayList=new ArrayList<>();
        databaseMileage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                carMileageModelArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CarMileageModel carMileageModel = postSnapshot.getValue(CarMileageModel.class);
                    carMileageModelArrayList.add(carMileageModel);
                }
                Log.d("Mileage from database", String.valueOf(carMileageModelArrayList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

