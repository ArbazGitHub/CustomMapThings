package com.techno.googlemap;

import android.app.Application;
import android.location.Location;

/**
 * Created by Arbaz.
 * Date: 17/4/18
 * Time: 11:33 AM
 */
public class CustomMapApplication extends Application{
    public static double latitude = 0.0;
    public static double longitude = 0.0;
    public static Location mCurrentLocation;
}
