package com.techno.googlemap.Global;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by Arbaz.
 * Date: 16/4/18
 * Time: 2:46 PM
 */
public class AppConstants {
    public static final String LAT_LONG_FILE="latlong.json";
    public static final String LAT_LONG_FULL_FILE="Fulllatlong.json";

    //Related to permission
    public static final String LOCATION_PERMISSION = ACCESS_FINE_LOCATION;
    public static final String READ_PHONE_STATE_PERMISSION= READ_PHONE_STATE;
    public static final String APP_NAME = "Custom Map";
}
