package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

public class FluffActivity extends AppCompatActivity {
    public static final int LOCATION_REQUEST_CODE = 420;

    private Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluff);
        locationButton = findViewById(R.id.locationButton);
    }

    public void checkEarthquake(View view) {

    }

    public void checkLocation(View view) {
        if (!locationButton.getText().equals("Are you sure?")) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                        , LOCATION_REQUEST_CODE);
                checkEarthquake(view);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    LocationManager locationManager = ((LocationManager) getSystemService(LOCATION_SERVICE));
                    if (locationManager.getAllProviders().size() > 0) {
                        Toast.makeText(this, "You're on Earth, congrats", Toast.LENGTH_SHORT).show();
                        locationButton.setText("Are you sure?");
                    }
                }
            }
        } else {
            startActivity(new Intent(this, MapsActivity.class));
        }
    }
}