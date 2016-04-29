package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by shadow on 16/1/23.
 */
public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {
    private CanChildScrollUpCallback mCanChildScrollUpCallback;

    public SwipeRefreshLayout(Context context) {
        super(context);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }

    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }


    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }

}
