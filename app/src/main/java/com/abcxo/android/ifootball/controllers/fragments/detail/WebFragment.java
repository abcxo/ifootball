package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.NestedWebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by shadow on 15/11/4.
 */
public class WebFragment extends DetailFragment {


    public String url;
    public String title;
    private NestedWebView webView;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener listener;

    public static WebFragment newInstance() {
        return newInstance(null);
    }

    public static WebFragment newInstance(Bundle args) {
        WebFragment fragment = new WebFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.packageName = Constants.PACKAGE_NAME_X5;
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if (args != null) {
            url = args.getString(Constants.KEY_URL);
            title = args.getString(Constants.KEY_TITLE);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        webView = (NestedWebView) view.findViewById(R.id.webview);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
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
            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (TextUtils.isEmpty(title)) {
                    activity.getSupportActionBar().setTitle(title);
                }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(R.string.menu_item_web_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menu.add(R.string.menu_item_web_open).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

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
        NavUtils.toBrowser(getActivity(), url);
    }


    @Override
    public void onResume() {
        super.onResume();
        Application.packageName = Constants.PACKAGE_NAME_X5;
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


    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }


}