package com.abcxo.android.ifootball.restfuls;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by shadow on 15/11/14.
 */
public abstract class OnRestful<T> implements retrofit.Callback<T> {

    abstract void onSuccess(T t);

    abstract void onError(RestfulError error);

    abstract void onFinish();

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            onSuccess(response.body());
        } else {
            Error error = new Error();
            try {
                error = (Error) retrofit.responseConverter(Error.class, Error.class.getAnnotations()).convert(response.errorBody());

            } catch (Exception e) {
                e.printStackTrace();
                error.code = RestfulError.ERROR_CODE_UNKNOWN;
                error.message = ViewUtils.getString(R.string.error_unknown);
            }
            onError(new RestfulError(error.code, error.message));
        }
        onFinish();
    }

    @Override
    public void onFailure(Throwable t) {
        onError(new RestfulError(t.getMessage()));
        onFinish();
    }

    public class Error {
        @SerializedName("status")
        public int code;
        @SerializedName("message")
        public String message;
    }
}
