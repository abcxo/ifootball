package com.abcxo.android.ifootball.views.x5;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.abcxo.android.ifootball.constants.Constants;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebSettings;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by shadow on 16/1/16.
 */
public class WebView extends com.tencent.smtt.sdk.WebView {
    public WebView(Context context) {
        super(context);
        init();
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        init();
    }

    public WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        init();
    }


    public void init() {
        WebSettings webSettings = getSettings();
        setCookies();
        CookieManager.getInstance().setAcceptCookie(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCachePath(Constants.DIR_TWEET_CACHE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setSaveFormData(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setCookies() {
        try {
            CookieManager.getInstance().setAcceptThirdPartyCookies(true);
            Field field= Class.forName("com.tencent.smtt.sdk.WebView").getDeclaredField("h");
            field.setAccessible(true);
            android.webkit.CookieManager.getInstance().setAcceptThirdPartyCookies((android.webkit.WebView) field.get(this),true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
