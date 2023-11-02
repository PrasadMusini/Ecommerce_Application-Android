package com.location.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPS extends AppCompatActivity {

    public static final int REQUEST_CODE = 10;
    private static final String TAG = "GPS";
    public static final int INTERVAL_MILLIS = 1000;
    public static final int FASTEST_INTERVAL_MILLIS = INTERVAL_MILLIS / 2;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private LocationSettingsRequest locationSettingsRequest;
    private SettingsClient settingsClient;
    private Location lastLocation;
    private Switch switch_btn;
    private boolean isLocationUpdatesEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            askPermission();
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        location_request();
        location_callBack();
        location_settingsRequest();

        switch_btn = findViewById(R.id.switch_btn);
        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startLocationUpdates();
                } else {
                    stopLocationUpdates();
                }
            }
        });
    }

    private void location_request() {

        mLocationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build();
    }

    private void location_settingsRequest() {
        // Configure location settings
        locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
                .build();
    }

    private void location_callBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                if (isLocationUpdatesEnabled) {
                    for (Location location : locationResult.getLocations()) {
                        if (lastLocation != null) {
                            float distance = location.distanceTo(lastLocation);
                            if (distance >= 50) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                lastLocation = location;
                                Log.d(TAG, "onLocationResult: " + latitude + " , " + longitude);
                            }
                        } else {
                            lastLocation = location;

                            double latitudeX = lastLocation.getLatitude();
                            double longitudeY = lastLocation.getLongitude();
                            Log.d(TAG, "onLocationResult: " + latitudeX + " , " + longitudeY);
                        }
                    }
                }
                super.onLocationResult(locationResult);
            }
        };
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(GPS.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with location-related operations.

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(GPS.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explain why this permission is needed to the user and potentially show a dialog.
                Toast.makeText(this, "Rational Message", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Rational Message");
            } else {
                // Permission denied, handle this case (e.g., inform the user or gracefully degrade functionality).
                showSettingsDialog();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission is required")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Permission is required to use location related stuff")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void startLocationUpdates() {
        // Request location updates
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        // All location settings are satisfied. Request location updates.
                        if (ActivityCompat.checkSelfPermission(GPS.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(GPS.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
                            isLocationUpdatesEnabled = true;
                        } else {
                            askPermission();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();

                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                                try {
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(GPS.this, 0);
                                } catch (IntentSender.SendIntentException sie) {
                                    // Ignore the error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied, and no way to fix the settings.
                                Toast.makeText(GPS.this, "Location settings are not satisfied. Please enable location services.", Toast.LENGTH_LONG).show();
//                                locationSwitch.setChecked(false);
                                Log.d(TAG, "Location settings are not satisfied. Please enable location services.");
                                isLocationUpdatesEnabled = false;
                        }
                    }
                });
    }

    private void stopLocationUpdates() {
        // Stop location updates
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        isLocationUpdatesEnabled = false;
    }
}