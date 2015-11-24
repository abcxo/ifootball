package com.abcxo.android.ifootball;

import com.abcxo.android.ifootball.utils.LocationUtils;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by shadow on 15/11/14.
 */
public class Application extends android.app.Application {
    public static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        ShareSDK.initSDK(this);
        LocationUtils.saveLocation();
    }

}
