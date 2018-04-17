package com.techno.googlemap.Screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.techno.googlemap.Global.AppConstants;
import com.techno.googlemap.Listener.OnInfoWindowElemTouchListener;
import com.techno.googlemap.Model.LatLongClusterItem;
import com.techno.googlemap.Model.LatLongItem;
import com.techno.googlemap.R;
import com.techno.googlemap.Utils.CustomInfoWindow.InfoWindowWrapperLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClusterActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    MapFragment mapFragment;
    InfoWindowWrapperLayout mapWrapperLayout;
    Marker marker;
    private CameraUpdate cu;
    //for cluster
    private ClusterManager<LatLongClusterItem> mClusterManager;
      CustomClusterRenderer renderer;

    ViewGroup infoWindow;
    LinearLayout llInfo;
    TextView infoTitle;
    TextView infoSnippet;
    Button btnLat, btnLong;
    private OnInfoWindowElemTouchListener infoLayout;
    private OnInfoWindowElemTouchListener infoButtonLat;
    private OnInfoWindowElemTouchListener infoButtonLong;

    ArrayList<LatLongClusterItem> latLongClusterItemArrayList= new ArrayList<>();
    LatLongClusterItem latLongClusterItem;
    Gson gson = new Gson();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster);
        try {
            initViews();
            try {
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject getFirst = jsonArray.getJSONObject(i);
                    LatLongItem latLongItem= gson.fromJson(getFirst.toString(), LatLongItem.class);
                    latLongClusterItem=new LatLongClusterItem(latLongItem.getLatitude(),latLongItem.getLongitude(),latLongItem.getState());
                    latLongClusterItemArrayList.add(latLongClusterItem);
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
            /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.9513, -92.3809), 5));

            for (int i = 0; i < latLongItemArrayList.size(); i++) {
                latLongItem = latLongItemArrayList.get(i);
                marker = mMap.addMarker(new MarkerOptions()
                        .title(latLongItem.getState())
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                        .position(new LatLng(latLongItem.getLatitude(), latLongItem.getLongitude())));

                dropPinEffect(marker);
                marker.setTag(latLongItem);

            }*/

            //TODO : MAKE SURE YOU WILL NEED MODEL TO USE ClusterManager
            mClusterManager = new ClusterManager<LatLongClusterItem>(this, googleMap);

            //it will expand the cluster while clicking on it
            googleMap.setOnCameraIdleListener(mClusterManager);

            //click listener of marker
            googleMap.setOnMarkerClickListener(mClusterManager);

            // CustomClusterRenderer to change the marker color or set image
              renderer = new CustomClusterRenderer(this, googleMap, mClusterManager);
            mClusterManager.setRenderer(renderer);

//            // for Custom Info View Adapter
//            mClusterManager.getMarkerCollection()
//                    .setOnInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));

            //setting infowindowadapter on the marker in cluster
            googleMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

            //click on infowindowadapter on the marker in cluster
            googleMap.setOnInfoWindowClickListener(mClusterManager);



            for (int i = 0; i < latLongClusterItemArrayList.size(); i++) {
//                latLongItem = latLongItemArrayList.get(i);
//                marker = mMap.addMarker(new MarkerOptions()
//                        .title(latLongItem.getState())
//                        .snippet("")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
//                        .position(new LatLng(latLongItem.getLatitude(), latLongItem.getLongitude())));
//
//                dropPinEffect(marker);

                //marker.setTag(latLongClusterItem);

                mClusterManager.addItem(latLongClusterItemArrayList.get(i));
                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(latLongClusterItemArrayList.get(i).getLatitude(), latLongClusterItemArrayList.get(i).getLongitude()), 5);
            }

            mClusterManager.cluster();

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(cu);
                }
            });

            //cluster click listener
            mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<LatLongClusterItem>() {
                @Override
                public boolean onClusterClick(Cluster<LatLongClusterItem> cluster) {
                    Toast.makeText(ClusterActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();
                    renderer.getMarker(cluster).hideInfoWindow();
                    return true;//todo if you want to hide infoWindow on cluster click make it true or else set false

                }
            });

            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<LatLongClusterItem>() {
                @Override
                public boolean onClusterItemClick(LatLongClusterItem locationModel) {
                    latLongClusterItem=locationModel;
                    Toast.makeText(ClusterActivity.this, "You have clicked on " + locationModel.getState(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });



            mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<LatLongClusterItem>() {
                @Override
                public void onClusterItemInfoWindowClick(LatLongClusterItem locationModel) {
                    Toast.makeText(ClusterActivity.this, "Clicked info window: " + locationModel.getState(),
                            Toast.LENGTH_SHORT).show();

                }
            });
            //Button Click
            this.infoButtonLat = new OnInfoWindowElemTouchListener(btnLat,
                    getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                   // latLongClusterItem = (LatLongClusterItem) marker.getTag();
                    // Here we can perform some action triggered after clicking the button
                    Toast.makeText(ClusterActivity.this, "Latitude:" + latLongClusterItem.getLatitude(), Toast.LENGTH_SHORT).show();
                }
            };
            this.btnLat.setOnTouchListener(infoButtonLat);

            //Button Click
            this.infoButtonLong = new OnInfoWindowElemTouchListener(btnLong,
                    getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                   // latLongClusterItem = (LatLongClusterItem) marker.getTag();
                    // Here we can perform some action triggered after clicking the button
                    Toast.makeText(ClusterActivity.this, "Longitude:" + latLongClusterItem.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            };
            this.btnLong.setOnTouchListener(infoButtonLong);

            //Layout Click
            this.infoLayout = new OnInfoWindowElemTouchListener(llInfo, getResources().getDrawable(R.drawable.rect_bg_primary),
                    getResources().getDrawable(R.drawable.rect_bg_primary)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                   // latLongClusterItem = (LatLongClusterItem) marker.getTag();
                    Toast.makeText(ClusterActivity.this, "Latitude:" + latLongClusterItem.getLatitude() + "," + "Longitude:" + latLongClusterItem.getLongitude(), Toast.LENGTH_SHORT).show();

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
            InputStream is = this.getAssets().open(AppConstants.LAT_LONG_FULL_FILE);
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
    /*
     *  CustomClusterRenderer class
     *  to create marker/ cluster icon using bitmap or using colors
     */
    public class CustomClusterRenderer extends DefaultClusterRenderer<LatLongClusterItem> {

        Context context;

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<LatLongClusterItem> clusterManager) {
            super(context, map, clusterManager);
            this.context = ClusterActivity.this;
        }

        ////customize the marker
        @Override
        protected void onBeforeClusterItemRendered(LatLongClusterItem item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);

            // for image
            final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin);
            markerOptions.icon(markerDescriptor).snippet(item.getSnippet());
            // for change color
            //final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        }

        //customize the cluster icon
        @Override
        protected void onBeforeClusterRendered(Cluster<LatLongClusterItem> cluster, MarkerOptions markerOptions) {
            super.onBeforeClusterRendered(cluster, markerOptions);

            //to customize the icon of cluster on map
            final IconGenerator mClusterIconGenerator;
            // in constructor
            mClusterIconGenerator = new IconGenerator(context.getApplicationContext());

            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(context, R.drawable.background_circle));
            mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);

            final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
    }

}
