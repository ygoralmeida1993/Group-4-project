package com.example.trip_plan_budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
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
    DatabaseReference databaseMileage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        carType =  findViewById(R.id.carType);
        databaseMileage= FirebaseDatabase.getInstance().getReference("mileage");
        carBrand=findViewById(R.id.CarBrands);
        TextView txtTitle = (TextView) findViewById(R.id.content_text);
        TextView carBrands = (TextView) findViewById(R.id.CarBrand);
        TextView CarMakes = (TextView) findViewById(R.id.CarMakes);
        carType.setVisibility(View.GONE);
        placeDetailsModelArrayList = new ArrayList<PlaceDetailsModel>();
        Bundle bundle = getIntent().getExtras();
        //placeDetailsModelArrayList = (ArrayList<PlaceDetailsModel>) bundle.getSerializable("placeDetailsModelArrayList");
        placeDetailsModelArrayList=this.getIntent().getExtras().getParcelableArrayList("placeDetailsModelArrayList");
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        carMake=(Spinner) findViewById(R.id.CarMake);
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
                if(carType.isShown()){
                    carType.setVisibility(View.GONE);
                    carType.startAnimation(animationUp);
                }
                else{
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
                if(carBrand.isShown()){
                    carBrand.setVisibility(View.GONE);
                    carBrand.startAnimation(animationUp);
                }
                else{
                    carMake.setVisibility(View.GONE);
                    carType.setVisibility(View.GONE);
                    carBrand.setVisibility(View.VISIBLE);
                    carBrand.startAnimation(animationDown);
                }
            }
        });

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

