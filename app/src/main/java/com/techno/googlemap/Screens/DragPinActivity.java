package com.techno.googlemap.Screens;

import android.app.Activity;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techno.googlemap.CustomMapApplication;
import com.techno.googlemap.Fragment.CustomMapFragment;
import com.techno.googlemap.Global.AppLog;
import com.techno.googlemap.R;
import com.techno.googlemap.Utils.Animation.AnimUtils;
import com.techno.googlemap.Utils.Cluster.MapWrapperLayout;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DragPinActivity extends CustomMapBaseActivity implements MapWrapperLayout.OnDragListener, OnMapReadyCallback {

    // Google Map
    private GoogleMap googleMap;
    private CustomMapFragment mCustomMapFragment;

    private View mMarkerParentView;
    private ImageView mMarkerImageView;

    private int imageParentWidth = -1;
    private int imageParentHeight = -1;
    private int imageHeight = -1;
    private int centerX = -1;
    private int centerY = -1;

    private TextView mLocationTextView;
    private Double sourceLatitude;
    private Double sourceLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_pin);

        // InitializeUI
        initializeUI();

        //set current location address
        setYourLocation();


    }

    private void initializeUI() {
        try {
            initilizeMap();
            mLocationTextView = (TextView) findViewById(R.id.location_text_view);
            mMarkerParentView = findViewById(R.id.marker_view_incl);
            mMarkerImageView = (ImageView) findViewById(R.id.marker_icon_view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        imageParentWidth = mMarkerParentView.getWidth();
        imageParentHeight = mMarkerParentView.getHeight();
        imageHeight = mMarkerImageView.getHeight();

        centerX = imageParentWidth / 2;
        centerY = (imageParentHeight / 2) + (imageHeight / 2);
    }

    private void initilizeMap() {
        if (googleMap == null) {
            mCustomMapFragment = ((CustomMapFragment) getFragmentManager()
                    .findFragmentById(R.id.map));
            mCustomMapFragment.setOnDragListener(DragPinActivity.this);
            mCustomMapFragment.getMapAsync(this);
            // check if map is created successfully or not
            /*if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }*/
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {


      /*  RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);
        mMarkerImageView.setAnimation(anim);*/
        Log.e("Drag Start", "Start");
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            Projection projection = (
                    googleMap != null &&
                            googleMap.getProjection() != null) ?
                    googleMap.getProjection()
                    : null;
            if (projection != null) {
                LatLng centerLatLng =
                        projection.fromScreenLocation(new Point(centerX, centerY));
                updateLocation(centerLatLng);
            }
        }
    }

    private void updateLocation(LatLng centerLatLng) {
        Log.e("Drag End", "End");
        try {

            if (centerLatLng != null) {
                Geocoder geocoder = new Geocoder(DragPinActivity.this,
                        Locale.getDefault());

                List<Address> addresses = new ArrayList<Address>();
                try {
                    addresses = geocoder.getFromLocation(centerLatLng.latitude,
                            centerLatLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses != null && addresses.size() > 0) {
                    String addressIndex0 = "",
                            addressIndex1 = "",
                            addressIndex2 = "",
                            addressIndex3 = "";
                    if (addresses.get(0).getAddressLine(0) != null) {
                        addressIndex0 = addresses.get(0).getAddressLine(0);
                        Log.e("Loction 0:-", addressIndex0);
                    }
                    if (addresses.get(0).getAddressLine(1) != null) {
                        addressIndex1 = addresses.get(0).getAddressLine(1);
                        Log.e("Loction 1:-", addressIndex1);
                    }
                    if (addresses.get(0).getAddressLine(2) != null) {
                        addressIndex2 = addresses.get(0).getAddressLine(2);
                        Log.e("Loction 2:-", addressIndex2);
                    }
                    if (addresses.get(0).getAddressLine(3) != null) {
                        addressIndex3 = addresses.get(0).getAddressLine(3);
                        Log.e("Loction 3:-", addressIndex3);
                    }

//                    String addressIndex0 = (
//                            addresses.get(0).getAddressLine(0) != null)
//                            ? addresses.get(0).getAddressLine(0) : null;
//                    String addressIndex1 =
//                            (addresses.get(0).getAddressLine(1) != null)
//                                    ? addresses.get(0).getAddressLine(1) : null;
//                    String addressIndex2 = (
//                            addresses.get(0).getAddressLine(2) != null)
//                            ? addresses.get(0).getAddressLine(2) : null;
//                    String addressIndex3 = (
//                            addresses.get(0).getAddressLine(3) != null)
//                            ? addresses.get(0).getAddressLine(3) : null;

                    String completeAddress = addressIndex0 + "," + addressIndex1;

                    if (!addressIndex2.equals("") && addressIndex2 != null) {
                        completeAddress += "," + addressIndex2;
                        Log.e("Location 0+1+2", completeAddress);
                    }
                    if (!addressIndex3.equals("") && addressIndex3 != null) {
                        completeAddress += "," + addressIndex3;
                        Log.e("Location 0+1+2+3", completeAddress);
                    }
                    if (completeAddress != null) {
                        Log.e("Location 0+1+2+3", completeAddress);
                        mLocationTextView.setText(completeAddress);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
          /*googleMap.addMarker(new MarkerOptions()
                    .title("London")
                    .snippet("United Kingdom")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                    .position(new LatLng(51.51, -0.1)));*/

        //only camera move
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(sourceLatitude ,sourceLongitude)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sourceLatitude ,sourceLongitude), 12.0f));
    }


    //set default location  into origin
    public void setYourLocation() {
        try {
            sourceLatitude = CustomMapApplication.latitude;
            sourceLongitude = CustomMapApplication.longitude;
            //Remove extra digits after decimal(limit  is 6)
            DecimalFormat df = new DecimalFormat("#.000000");
            if (sourceLongitude != null && sourceLatitude != null) {
                sourceLatitude = Double.valueOf(df.format(sourceLatitude));
                sourceLongitude = Double.valueOf(df.format(sourceLongitude));
            }
            //for address based on lat lang
            if (sourceLatitude != null && sourceLongitude != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(sourceLatitude, sourceLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                //end here
                if (!address.isEmpty() && address != null) {
                    //set address
                    mLocationTextView.setText(address);
//                        etOrigin.clearFocus();
//                        etDestination.requestFocus();
//                        Global.storePreference(Constants.YOUR_LOCATION, true);
//                        updateMarkerOnMap();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}