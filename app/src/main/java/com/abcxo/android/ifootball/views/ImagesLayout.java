package com.abcxo.android.ifootball.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by shadow on 15/11/2.
 */
public class ImagesLayout extends RelativeLayout{
    public ImagesLayout(Context context) {
        super(context);
    }

    public ImagesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImagesLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
