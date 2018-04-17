package com.techno.googlemap.Screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.techno.googlemap.Global.AppConstants;
import com.techno.googlemap.Global.AppDialog;
import com.techno.googlemap.Global.AppGlobal;
import com.techno.googlemap.Global.AppLog;
import com.techno.googlemap.R;

import static com.techno.googlemap.Global.AppGlobal.REQUEST_PERMISSION_SETTING;

public class SplashActivity extends CustomMapBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.btnPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (needPermission()) {
                        selectionActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Getting permission here
    public boolean needPermission() {
        try {
            int location = ContextCompat.checkSelfPermission(this, AppConstants.LOCATION_PERMISSION);
            if (location == PackageManager.PERMISSION_GRANTED) {
                selectionActivity();
                return true;
            } else {
                if (location != PackageManager.PERMISSION_GRANTED) {
                    AppGlobal.checkSinglePermission(this, AppConstants.LOCATION_PERMISSION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Permission request result here
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                AppLog.e("denied" + permission);//denied
                needPermission();//needed permission for getting Country code

            } else {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    AppLog.e("allowed" + permission);//allowed
                    needPermission();//set data
                } else {
                    //set to never ask again
                    AppLog.e("set to never ask again" + permission);
                    AppDialog.showAlertDialog(this, null,
                            getString(R.string.read_phone_state_permission_txt), getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    AppGlobal.permissionSettingView(SplashActivity.this);

                                }
                            });

                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_PERMISSION_SETTING) {
                needPermission();
            } else {
                //Write code here
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectionActivity() {
        try {
            Intent intent = new Intent(SplashActivity.this, SelectionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
