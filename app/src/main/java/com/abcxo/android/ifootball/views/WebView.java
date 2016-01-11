package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;

import java.util.Map;

/**
 * Created by shadow on 16/1/10.
 */
public class WebView extends com.tencent.smtt.sdk.WebView {


    public WebView(Context context) {
        super(context);
        setWebSettings(getSettings());
    }

    public WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWebSettings(getSettings());
    }

    public WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setWebSettings(getSettings());

    }

    public WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        setWebSettings(getSettings());
    }

    public WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        setWebSettings(getSettings());
    }

    private void setWebSettings(WebSettings paramWebSettings) {
        paramWebSettings.setJavaScriptEnabled(true);
        paramWebSettings.setAllowFileAccess(true);
        paramWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        paramWebSettings.setSupportZoom(true);
        paramWebSettings.setBuiltInZoomControls(true);
        paramWebSettings.setUseWideViewPort(true);
        paramWebSettings.setSupportMultipleWindows(false);
        paramWebSettings.setLoadWithOverviewMode(true);
        paramWebSettings.setAppCacheEnabled(true);
        paramWebSettings.setDatabaseEnabled(true);
        paramWebSettings.setDomStorageEnabled(true);
        paramWebSettings.setGeolocationEnabled(true);
        paramWebSettings.setAppCacheMaxSize(9223372036854775807L);
        paramWebSettings.setAppCachePath(getContext().getDir("appcache", 0).getPath());
        paramWebSettings.setDatabasePath(getContext().getDir("databases", 0).getPath());
        paramWebSettings.setGeolocationDatabasePath(getContext().getDir("geolocation", 0).getPath());
        paramWebSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        paramWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        System.currentTimeMillis();
        CookieSyncManager.createInstance(getContext());
        CookieSyncManager.getInstance().sync();
    }

}
