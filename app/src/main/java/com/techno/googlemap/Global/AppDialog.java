package com.techno.googlemap.Global;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techno.googlemap.R;

/**
 * Created by Arbaz.
 * Date: 17/4/18
 * Time: 11:55 AM
 */
public class AppDialog {
    public static ProgressDialog progressDialog;

    private static Dialog d;

    //Related to dialog
    public static Dialog cProgress;
    public static ImageView ivProgress;
    public static TextView tvProgressTitle;
    Animation.AnimationListener listener;

    //with one button
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText,
                                       DialogInterface.OnClickListener _onPositiveClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setCancelable(false);
        dialog.show();
        Button bq = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.BLACK);
    }

    //with two buttons
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText, String _negativeText,
                                       DialogInterface.OnClickListener _onPositiveClick,
                                       DialogInterface.OnClickListener _onNegativeClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, _negativeText,
                _onNegativeClick);
        dialog.setCancelable(false);
        dialog.show();
        Button bPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button bNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bPositive.setTextColor(Color.BLACK);
        bNegative.setTextColor(Color.BLACK);
    }

    //with three buttons
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText, String _negativeText, String _neutralText,
                                       DialogInterface.OnClickListener _onPositiveClick,
                                       DialogInterface.OnClickListener _onNegativeClick, Dialog.OnClickListener _onNeutralClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, _negativeText,
                _onNegativeClick);
        dialog.setButton(Dialog.BUTTON_NEUTRAL, _neutralText,
                _onNeutralClick);
        dialog.setCancelable(false);
        dialog.show();
        Button bPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button bNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        bPositive.setTextColor(Color.BLACK);
        bNegative.setTextColor(Color.BLACK);
        bNeutral.setTextColor(Color.BLACK);
    }


}

