package com.techno.googlemap.Global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Arbaz.
 * Date: 17/4/18
 * Time: 11:50 AM
 */
public class AppGlobal {
    public static final int SINGLE_PERMISSION_REQUEST = 2001;
    public static final int REQUEST_PERMISSION_SETTING = 2002;
    //check single permission
    public static boolean checkSinglePermission(final Context context, String permissionType) {
        ArrayList<String> permissionList = new ArrayList<>();
        String type = "";

        if (permissionType != null) {

            if (permissionType.equals(AppConstants.READ_PHONE_STATE_PERMISSION)) {
                type = permissionType;
            }
            if (permissionType.equals(AppConstants.LOCATION_PERMISSION)) {
                type = permissionType;
            }

            int getPermission = ContextCompat.checkSelfPermission(context, type);

            if (getPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(type);
                if (!permissionList.isEmpty()) {
                    ActivityCompat.requestPermissions((Activity) context, permissionList.toArray
                            (new String[permissionList.size()]), SINGLE_PERMISSION_REQUEST);
                    return false;
                }
            }

        }
        return true;
    }
    //open setting screen
    public static void permissionSettingView(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult((Activity) context, intent, REQUEST_PERMISSION_SETTING, null);
    }
}
