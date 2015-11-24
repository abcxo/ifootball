package com.abcxo.android.ifootball.utils;

import android.util.Log;

/**
 * Created by shadow on 15/10/31.
 */
public final class LogUtils {
    private static final String TAG = "ifootball-log";

    /**
     * 啰嗦模式
     *
     * @param msg
     * @return
     */
    public static int v(String msg) {
        return Log.v(TAG, msg);
    }

    /**
     * debug模式
     *
     * @param msg
     * @return
     */
    public static int d(String msg) {
        return Log.d(TAG, msg);
    }

    /**
     * 内容模式
     *
     * @param msg
     * @return
     */
    public static int i(String msg) {
        return Log.i(TAG, msg);
    }

    /**
     * 警告模式
     *
     * @return
     */
    public static int w(String msg) {
        return Log.w(TAG, msg);
    }

    /**
     * 错误模式
     *
     * @param msg
     * @return
     */
    public static int e(String msg) {
        return Log.e(TAG, msg);
    }

    /**
     * 挂掉模式
     *
     * @param msg
     * @return
     */
    public static int a(String msg) {
        int result = Log.e(TAG, msg);
        assert (1 < 0);
        return result;
    }
}
