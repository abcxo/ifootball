package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shadow on 15/10/31.
 */
public class IconFontVIew extends TextView{

    private static final String ICONFONT_NAME = "iconfont.ttf";
    private static Typeface iconfont = null;

    public IconFontVIew(Context context) {
        super(context);
        prepare();
    }

    public IconFontVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepare();
    }

    public IconFontVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepare();
    }

    public IconFontVIew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepare();
    }

    private void prepare() {
        try {
            if (iconfont == null) {
                iconfont = Typeface.createFromAsset(getContext().getAssets(), ICONFONT_NAME);
            }
            setTypeface(iconfont);
        } catch (Exception e) {
            iconfont = null;

        }

        setIncludeFontPadding(false);
    }
}
