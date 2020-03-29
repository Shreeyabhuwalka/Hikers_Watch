package com.example.hikerswatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    Location location;
    TextView latTV;
    TextView lonTV;
    TextView bearTV;
    TextView altTV;
    TextView speedTV;
    TextView accTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        location = locationManager.getLastKnownLocation(provider);
        if(location!=null)
        {
            Log.i("Location info","Location Achieved!adfrgdhfn");
        }
        else
        {
            Log.i("Location info","No location");
        }
        latTV = (TextView) findViewById(R.id.lat);
        lonTV = (TextView) findViewById(R.id.lon);
        accTV = (TextView) findViewById(R.id.acc);
        bearTV = (TextView) findViewById(R.id.bear);
        speedTV = (TextView) findViewById(R.id.speed);
        altTV = (TextView) findViewById(R.id.alt);

        onLocationChanged(location);
    }

    @Override
    public void onLocationChanged(Location location) {
    Double lat = location.getLatitude();
    Double lon = location.getLongitude();
    Double alt = location.getAltitude();
    Float bearing = location.getBearing();
    Float speed = location.getSpeed();
    Float accuracy = location.getAccuracy();

        Log.i("Location: Latitude", String.valueOf(lat));
        Log.i("Location: Longitude", String.valueOf(lon));
        Log.i("Location: Altitude", String.valueOf(alt));
        Log.i("Location: Bearing", String.valueOf(bearing));
        Log.i("Location: Speed", String.valueOf(speed));
        Log.i("Location: Accuracy", String.valueOf(accuracy));
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat,lon,1);
            if(listAddresses!=null && listAddresses.size()>0) {
                Log.i("Location info", listAddresses.get(0).toString());
            }
        } catch (IOException e) {
            Log.i("Location", "nnnnnnnnnnn");
            e.printStackTrace();
        }
        lat = Double.valueOf(Math.round(lat*100)/100);
        lon = Double.valueOf(Math.round(lon*100)/100);
        speed = Float.valueOf(Math.round(speed*100)/100);
        latTV.setText("Latitude: "+lat.toString());
        lonTV.setText("Longitude: "+lon.toString());
        speedTV.setText("Speed: "+speed.toString()+"m/s");
        altTV.setText("Altitude: "+alt.toString()+"m");
        accTV.setText("Accuracy: "+accuracy.toString()+"m");
        bearTV.setText("Bearing: "+bearing.toString());


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
