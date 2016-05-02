package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconFontView extends TextView {

    private static Typeface iconfont = null;

    public IconFontView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public IconFontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFontView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        try {
            if (iconfont == null) {
                iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
            }
            setTypeface(iconfont);
        } catch (Exception e) {
            iconfont = null;
        }

        setIncludeFontPadding(false);
    }

}
