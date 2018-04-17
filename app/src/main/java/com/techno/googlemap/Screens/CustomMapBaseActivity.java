package com.techno.googlemap.Screens;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.techno.googlemap.Global.AppLog;
import com.techno.googlemap.CustomMapApplication;
import com.techno.googlemap.Utils.Location.LocationHelper;


/**
 * Created by Arbaz.
 * Date: 17/4/18
 * Time: 12:03 PM
 */
public class CustomMapBaseActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    public LocationHelper locationHelper;
    public Location mLastLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            locationHelper = new LocationHelper(this);
            locationHelper.checkpermission();

            // check availability of play services
            if (locationHelper.checkPlayServices()) {
                // Building the GoogleApi client
                locationHelper.buildGoogleApiClient();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {
            CustomMapApplication.latitude = mLastLocation.getLatitude();
            CustomMapApplication.longitude = mLastLocation.getLongitude();
            //     Toast.makeText(this, "FromBase activity:" + "lat:" + mLastLocation.getLongitude()+ "," + "long:" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationHelper.connectApiClient();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AppLog.e("Connection failed:" + " ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void onLocationChanged(Location location) {
        CustomMapApplication.mCurrentLocation = location;
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
