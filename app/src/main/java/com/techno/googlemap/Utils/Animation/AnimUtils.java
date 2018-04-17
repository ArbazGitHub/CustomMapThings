package com.techno.googlemap.Utils.Animation;

import android.app.Activity;
import android.content.Context;

import com.techno.googlemap.R;

/**
 * Created by Arbaz.
 * Date: 17/4/18
 * Time: 1:58 PM
 */
public class AnimUtils {


    public static void activitySlideUpAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.activity_slide_up, android.R.anim.fade_out);

    }

    public static void activitySlidedownAnim(Context context) {
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, R.anim.activity_slide_down);
    }


}
