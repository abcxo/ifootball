package com.abcxo.android.ifootball.utils;

import com.abcxo.android.ifootball.constants.Constants;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

/**
 * Created by shadow on 15/12/7.
 */
public class NetworkUtils {
    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        int cacheSize = 1000 * 1024 * 1024; // 1000 MiB
        File cacheDirectory = new File(Constants.DIR_TWEET_CACHE);
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client.setCache(cache);
        return client;
    }

}
