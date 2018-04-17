package com.techno.googlemap.Utils.Cluster;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Arbaz.
 * Date: 16/4/18
 * Time: 5:19 PM
 */
public class MapWrapperLayout extends FrameLayout {

    public interface OnDragListener {
        public void onDrag(MotionEvent motionEvent);
    }

    private OnDragListener mOnDragListener;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mOnDragListener != null) {
            mOnDragListener.onDrag(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnDragListener(OnDragListener mOnDragListener) {
        this.mOnDragListener = mOnDragListener;
    }
}