package com.abcxo.android.ifootball.restfuls.interceptor;

import com.socks.library.KLog;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;

/**
 * Created by yoghourt on 5/5/16.
 */
public class DecryptedPayloadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse;

        if (originalRequest.body() != null) {
            RequestBody newBody = RequestBody.create(originalRequest.body().contentType(), requestBodyToString(originalRequest.body()));

//            KLog.e("RequestBody是 : " + requestBodyToString(originalRequest.body()));

            // Request customization: add request headers
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .method(originalRequest.method(), newBody);

            Request newRequest = requestBuilder.build();

            originalResponse = chain.proceed(newRequest);
        } else {
            originalResponse =  chain.proceed(originalRequest);
        }

        String responseContent = new Buffer().write(originalResponse.body().bytes()).readUtf8();

        KLog.e("请求返回的内容 : " + responseContent);

        Response newResponse = originalResponse.newBuilder().body(ResponseBody.create(originalResponse.body().contentType(), responseContent)).build();

        return newResponse;
    }

    private String requestBodyToString(final RequestBody requestBody) {
        try {
            final RequestBody copy = requestBody;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
