package parallelcodes.mysqlapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double currentLatitude;
    double currentLongitude;
double longitute,latitude;
    TextView distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        longitute = bundle.getDouble("long");
        latitude = bundle.getDouble("lat");
        Log.d("latlong", String.valueOf(longitute));
        Log.d("latlong", String.valueOf(latitude));
        distance = (TextView) this.findViewById(R.id.distance);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
        Location selected_location = new Location("locationA");
        selected_location.setLatitude(currentLatitude);
        selected_location.setLongitude(currentLongitude);

        Location near_locations = new Location("locationB");
        near_locations.setLatitude(latitude);
        near_locations.setLongitude(longitute);
        double dis = (selected_location.distanceTo(near_locations)) * 0.00062137;
        double roundOff = Math.round(dis * 100.0) / 100.0;
        String dist= String.valueOf(roundOff);
        distance.setText("Distance: "+dist+" miles");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
        LatLng location = new LatLng(latitude, longitute);
        mMap.addMarker(new MarkerOptions().position(location).title("Your Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
