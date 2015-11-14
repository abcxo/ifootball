package com.abcxo.android.ifootball.restfuls;

/**
 * Created by shadow on 15/11/3.
 */
public class RestfulError {
    public static final int ERROR_CODE_UNKNOWN = -1001;
    public int code;
    public String msg;

    public RestfulError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestfulError(String msg) {
        this(ERROR_CODE_UNKNOWN, msg);
    }
}
