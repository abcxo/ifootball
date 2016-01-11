package com.abcxo.android.ifootball;

import com.abcxo.android.ifootball.constants.Constants;

/**
 * Created by shadow on 15/11/14.
 */
public class Application extends android.app.Application {
    public static Application INSTANCE;

    public static String packageName = Constants.PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        INSTANCE = this;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }
}
