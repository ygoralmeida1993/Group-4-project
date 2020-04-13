package com.example.trip_plan_budget.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.trip_plan_budget.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    double currentLatitude;
    double currentLongitude;
    double longitude, latitude;
    TextView distance;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        longitude = Objects.requireNonNull(bundle).getDouble("long");
        latitude = bundle.getDouble("lat");
        Log.d("latlong", String.valueOf(longitude));
        Log.d("latlong", String.valueOf(latitude));
        distance = this.findViewById(R.id.distance);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = Objects.requireNonNull(lm).getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLongitude = Objects.requireNonNull(location).getLongitude();
        currentLatitude = location.getLatitude();
        Location selected_location = new Location("locationA");
        selected_location.setLatitude(currentLatitude);
        selected_location.setLongitude(currentLongitude);
        Location near_locations = new Location("locationB");
        near_locations.setLatitude(latitude);
        near_locations.setLongitude(longitude);
        double dis = (selected_location.distanceTo(near_locations)) * 0.00062137;
        double roundOff = Math.round(dis * 100.0) / 100.0;
        String dist = String.valueOf(roundOff);
        distance.setText(String.format("Distance: %s miles", dist));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // This object is used to define latitude and longitude for only Canada region.
        LatLngBounds ADELAIDE = new LatLngBounds(
                new LatLng(41.6765556, -141.00275), new LatLng(83.3362128, -52.3231981));
        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);  // Set boundaries for a location
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Your Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
