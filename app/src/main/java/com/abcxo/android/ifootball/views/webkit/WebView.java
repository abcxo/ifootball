package com.abcxo.android.ifootball.views.webkit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

import com.abcxo.android.ifootball.constants.Constants;

/**
 * Created by shadow on 16/1/16.
 */
public class WebView extends android.webkit.WebView {
    public WebView(Context context) {
        super(context);
        init();
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);

    }
}
