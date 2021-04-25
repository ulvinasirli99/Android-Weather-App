package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.database.DatabaseHandler;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private Context context;
    private Window window;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation;
    private Boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private DatabaseHandler databaseHandler;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        databaseHandler = new DatabaseHandler(context);

        getLocationPermissions();

        getDeviceLocation();

    }


    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }

    private void getDeviceLocation() {

        Log.d(TAG, "getDeviceLocation: getDeviceLocation getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            currentLocation = (Location) task.getResult();

                            String address = "";

                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                address = addresses.get(0).getAddressLine(0);
                                Log.d(TAG, "onComplete: Yerr : " + address);
                                Log.d(TAG, "onComplete: Yerr : " + currentLocation.getLatitude());
                                Log.d(TAG, "onComplete: Yerr : " + currentLocation.getLongitude());


                                final Boolean res = databaseHandler.checkIsRowExists(address);

                                final String finalAddress = address;
                                Thread timer = new Thread() {
                                    public void run() {
                                        try {
                                            sleep(2900);
                                            if (res == true) {
                                                Intent intent = new Intent(context, HomeActivity.class);
                                                intent.putExtra("LT", currentLocation.getLatitude());
                                                intent.putExtra("LG", currentLocation.getLongitude());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                databaseHandler.InsertColumn(finalAddress);
                                                Intent intent = new Intent(context, HomeActivity.class);
                                                intent.putExtra("LT", currentLocation.getLatitude());
                                                intent.putExtra("LG", currentLocation.getLongitude());
                                                startActivity(intent);
                                                finish();
                                            }

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                timer.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(context, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }


    }

}