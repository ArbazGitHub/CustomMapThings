package com.techno.googlemap.Screens;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.techno.googlemap.Global.AppConstants;
import com.techno.googlemap.Listener.OnInfoWindowElemTouchListener;
import com.techno.googlemap.Model.LatLongItem;
import com.techno.googlemap.R;
import com.techno.googlemap.Utils.CustomInfoWindow.InfoWindowWrapperLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CustomMarker extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    MapFragment mapFragment;
    InfoWindowWrapperLayout mapWrapperLayout;
    Marker marker;

    ViewGroup infoWindow;
    LinearLayout llInfo;
    TextView infoTitle;
    TextView infoSnippet;
    Button btnLat, btnLong;
    private OnInfoWindowElemTouchListener infoLayout;
    private OnInfoWindowElemTouchListener infoButtonLat;
    private OnInfoWindowElemTouchListener infoButtonLong;

    ArrayList<LatLongItem> latLongItemArrayList = new ArrayList<>();
    LatLongItem latLongItem;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_marker);
        try {
            initViews();
            try {
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject getFirst = jsonArray.getJSONObject(i);
                    LatLongItem latLongItem = gson.fromJson(getFirst.toString(), LatLongItem.class);
                    latLongItemArrayList.add(latLongItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initViews() {
        try {

            mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapWrapperLayout = (InfoWindowWrapperLayout) findViewById(R.id.map_relative_layout);
            mapFragment.getMapAsync(this);
            this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window, null);
            this.llInfo = (LinearLayout) infoWindow.findViewById(R.id.llInfo);
            this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
            this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
            this.btnLat = (Button) infoWindow.findViewById(R.id.btnLat);
            this.btnLong = (Button) infoWindow.findViewById(R.id.btnLong);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("Map ready call", "");
        try {
            mMap = googleMap;
            mapWrapperLayout.init(mMap, getPixelsFromDp(this, 15 + 20));
            // TODO: 12/4/18 if you want to chnage click position on info window change getPixelsFromDp(this, 39 + 20));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.9513, -92.3809), 5));

            //Let's add a couple of markers
            /*dropPinEffect(mMap.addMarker(new MarkerOptions()
                    .title("London")
                    .snippet("United Kingdom")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                    .position(new LatLng(51.51, -0.1))));*/

            for (int i = 0; i < latLongItemArrayList.size(); i++) {
                latLongItem = latLongItemArrayList.get(i);
                marker = mMap.addMarker(new MarkerOptions()
                        .title(latLongItem.getState())
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                        .position(new LatLng(latLongItem.getLatitude(), latLongItem.getLongitude())));

                dropPinEffect(marker);
                marker.setTag(latLongItem);

            }
            //Button Click
            this.infoButtonLat = new OnInfoWindowElemTouchListener(btnLat,
                    getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    latLongItem = (LatLongItem) marker.getTag();
                    // Here we can perform some action triggered after clicking the button
                    Toast.makeText(CustomMarker.this, "Latitude:" + latLongItem.getLatitude(), Toast.LENGTH_SHORT).show();
                }
            };
            this.btnLat.setOnTouchListener(infoButtonLat);

            //Button Click
            this.infoButtonLong = new OnInfoWindowElemTouchListener(btnLong,
                    getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    latLongItem = (LatLongItem) marker.getTag();
                    // Here we can perform some action triggered after clicking the button
                    Toast.makeText(CustomMarker.this, "Longitude:" + latLongItem.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            };
            this.btnLong.setOnTouchListener(infoButtonLong);

            //Layout Click
            this.infoLayout = new OnInfoWindowElemTouchListener(llInfo, getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    latLongItem = (LatLongItem) marker.getTag();
                    Toast.makeText(CustomMarker.this, "Latitude:" + latLongItem.getLatitude() + "," + "Longitude:" + latLongItem.getLongitude(), Toast.LENGTH_SHORT).show();

                }
            };

            this.llInfo.setOnTouchListener(infoLayout);

            //Manage info window click
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    // TODO: 12/4/18 Set INFOWINDOW WITHOUT MARGIN

                    infoButtonLat.setMarker(marker);
                    infoTitle.setText(marker.getTitle());
                    infoSnippet.setText(marker.getSnippet());

                    infoButtonLat.setMarker(marker);
                    infoButtonLong.setMarker(marker);
                    infoLayout.setMarker(marker);

                    // We must call this to set the current marker and infoWindow references
                    // to the MapWrapperLayout
                    mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                    infoWindow.setBackground(getDrawable(R.drawable.rect_bg_primary));
                    return infoWindow;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // TODO: 12/4/18 Set INFOWINDOW WITH MARGIN
//                    infoButtonListener.setMarker(marker);
//                    infoTitle.setText(marker.getTitle());
//                    infoSnippet.setText(marker.getSnippet());
//                    infoButtonListener.setMarker(marker);
//                    infoButtonListener1.setMarker(marker);
//                    // We must call this to set the current marker and infoWindow references
//                    // to the MapWrapperLayout
//                    mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
//                    infoWindow.setBackground(getDrawable(R.drawable.rect_bg_primary));
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //load lat long
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open(AppConstants.LAT_LONG_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // pin Animation here
    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1000;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }

    //For set view on infoWindow based on scale
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
