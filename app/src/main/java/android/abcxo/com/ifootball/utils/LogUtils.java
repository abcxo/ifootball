package android.abcxo.com.ifootball.utils;

import android.util.Log;

/**
 * Created by shadow on 15/10/31.
 */
public final class LogUtils {
    /**
     * 啰嗦模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    /**
     * debug模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    /**
     * 内容模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    /**
     * 警告模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    /**
     * 错误模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    /**
     * 挂掉模式
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int a(String tag, String msg) {
        int result = Log.e(tag, msg);
        assert (1 < 0);
        return result;
    }
}
