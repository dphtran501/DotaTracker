package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class FluffActivity extends AppCompatActivity {

    public static final int LOCATION_REQUEST_CODE = 420;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluff);
    }

    public void checkEarthquake(View view) {

    }

    public void checkLocation(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                    , LOCATION_REQUEST_CODE);
            checkEarthquake(view);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocationManager locationManager = ((LocationManager) getSystemService(LOCATION_SERVICE));
                if(locationManager.registerGnssStatusCallback(GNSS_STATUS_LISTENER))
                    if(locationManager.getAllProviders().size() > 0)
                        Toast.makeText(this, "You're on Earth, congrats", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public final GnssStatus.Callback GNSS_STATUS_LISTENER = new GnssStatus.Callback() {

        @Override
        public void onStarted() {
            super.onStarted();
            Toast.makeText(FluffActivity.this, "You're on Earth", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {
            super.onSatelliteStatusChanged(status);
            Toast.makeText(FluffActivity.this, "You're Still on Earth", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStopped() {
            super.onStopped();
            Toast.makeText(FluffActivity.this, "Can't Tell Where You Are Anymore", Toast.LENGTH_SHORT).show();
        }
    };
}
