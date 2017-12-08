package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.dvoiss.literallytoast.LitToast;

import java.util.Timer;
import java.util.TimerTask;

public class FluffActivity extends AppCompatActivity {
    public static final int LOCATION_REQUEST_CODE = 420;
    public static final int[] COLORS = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
    private static final String TAG = "FluffActivity";
    ShakeListener mShakeListener;
    private long shakeTimeStamp;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    private Button locationButton;
    private long startMillis;
    private int count = 1;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluff);

        findViewById(R.id.linearLayout).setTag(R.id.key1, getBaseContext());
        locationButton = (Button) findViewById(R.id.locationButton);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeListener = new ShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                shakeTimeStamp = System.currentTimeMillis();
            }
        });
    }


    public void checkEarthquake(View view) {
        final long currentTime = System.currentTimeMillis();
        final Context context = this;
        Toast.makeText(context, "Checking for earthquake....", Toast.LENGTH_SHORT).show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentTime < shakeTimeStamp)
                            Toast.makeText(context, "There may be a earthquake currently", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context, "Definitely no earthquake!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }, 2 * 1000);


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
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    LocationManager locationManager = ((LocationManager) getSystemService(LOCATION_SERVICE));
                    if (locationManager.getAllProviders().size() > 0) {
                        Toast.makeText(this, "You're on Earth, congrats", Toast.LENGTH_SHORT).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        locationButton.setText("Are you sure?");
                                    }
                                });
                            }
                        }, 1 * 1000);

                    }
                }
            }
        } else {
            startActivity(new Intent(this, MapsActivity.class));
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            locationButton.setText("Where Am I Now?");
                        }
                    });
                }
            }, 1 * 1000);

        }
    }

    public void toast(View view) {
        LitToast.create(this, "Toast!", 2250).setPlayToasterSound(true).show();
    }

    public void fluffSecret(View view) {
        long time = System.currentTimeMillis();
        if (index == 4) index = 0;
        view.setBackgroundColor(COLORS[index++]);
        if (++count == 15) {
            Toast.makeText(this, "Aloha Friday Mode", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PauldingAnimation.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
}

