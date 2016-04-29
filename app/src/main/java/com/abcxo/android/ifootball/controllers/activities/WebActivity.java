package com.abcxo.android.ifootball.controllers.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.SwipeRefreshLayout;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by shadow on 15/11/4.
 */
public class WebActivity extends AppCompatActivity {

    private View customView;
    private int originalSystemUiVisibility;
    private int originalOrientation;
    private CustomViewCallback customViewCallback;


    public String url;
    public String title;
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener listener;

    public WebActivity() {
        Application.packageName = ViewUtils.isX5() ? Constants.PACKAGE_NAME_X5 : Constants.PACKAGE_NAME;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            url = args.getString(Constants.KEY_URL);
            title = args.getString(Constants.KEY_TITLE);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.webview);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        };
        refreshLayout.setOnRefreshListener(listener);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (TextUtils.isEmpty(title)) {
                    getSupportActionBar().setTitle(title);
                }

            }

            @Override
            public void onShowCustomView(View view,
                                         CustomViewCallback callback) {
                // if a view already exists then immediately terminate the new one
                if (view != webView) {
                    if (customView != null) {
                        onHideCustomView();
                        return;
                    }
                    // 1. Stash the current state
                    customView = view;

                    // 2. Stash the custom view callback
                    customViewCallback = callback;

                    // 3. Add the custom view to the view hierarchy
                    FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                    decor.addView(customView, new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                }


                originalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                originalOrientation = getRequestedOrientation();


                // 4. Change the state of the window
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }


            @Override
            public void onHideCustomView() {
                if (customView != null) {
                    // 1. Remove the custom view
                    FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                    decor.removeView(customView);
                    customView = null;
                    // 3. Call the custom view callback
                    customViewCallback.onCustomViewHidden();
                    customViewCallback = null;
                }


                // 2. Restore the state to it's original form
                getWindow().getDecorView()
                        .setSystemUiVisibility(originalSystemUiVisibility);
                setRequestedOrientation(originalOrientation);


            }
        });

        refreshLayout.setCanChildScrollUpCallback(new SwipeRefreshLayout.CanChildScrollUpCallback() {
            @Override
            public boolean canSwipeRefreshChildScrollUp() {
                return webView.getView().getScrollY() > 0;
            }
        });
        refresh();
    }


    public void refresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.menu_item_web_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menu.add(R.string.menu_item_web_open).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals(getString(R.string.menu_item_web_copy))) {
            copyUrl();
            return true;
        } else if (title.equals(getString(R.string.menu_item_web_open))) {
            openUrl();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void copyUrl() {
        ViewUtils.copy(url);
    }

    public void openUrl() {
        NavUtils.toBrowser(this, url);
    }


    @Override
    public void onResume() {
        super.onResume();
        Application.packageName = ViewUtils.isX5() ? Constants.PACKAGE_NAME_X5 : Constants.PACKAGE_NAME;
        if (webView != null) {
            webView.onResume();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
        Application.packageName = Constants.PACKAGE_NAME;
    }

    @Override
    public void onDestroy() {

        if (webView != null) {
            webView.destroy();
        }
        Application.packageName = Constants.PACKAGE_NAME;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

