package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;

/**
 * Created by shadow on 15/11/7.
 */
public class ViewUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = Application.INSTANCE.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = Application.INSTANCE.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static void toast(String str) {
        Toast.makeText(Application.INSTANCE, str, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int resId) {
        Toast.makeText(Application.INSTANCE, resId, Toast.LENGTH_SHORT).show();
    }

    public static String getString(int resId) {
        return Application.INSTANCE.getString(resId);
    }
}
