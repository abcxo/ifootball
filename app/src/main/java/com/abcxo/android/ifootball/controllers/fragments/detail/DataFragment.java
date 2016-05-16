package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Data;
import com.abcxo.android.ifootball.restfuls.DataRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.SwipeRefreshLayout;

public class DataFragment extends Fragment {

    private WebView webView;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    protected String name;
    protected String category;
    protected long uid;

    public static DataFragment newInstance() {
        return newInstance(null);
    }

    public static DataFragment newInstance(Bundle args) {
        DataFragment fragment = new DataFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uid = args.getLong(Constants.KEY_UID);
            name = args.getString(Constants.KEY_NAME);
            category = args.getString(Constants.KEY_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        refreshLayout.setColorSchemeResources(R.color.color_primary);

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        };
        refreshLayout.setOnRefreshListener(onRefreshListener);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);

            }
        });

        refreshLayout.setCanChildScrollUpCallback(new SwipeRefreshLayout.CanChildScrollUpCallback() {
            @Override
            public boolean canSwipeRefreshChildScrollUp() {
                return webView.getScrollY() > 0;
            }
        });
//        refresh();

    }


    public void refresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
    }


    protected void load() {
        Data data = (Data) FileUtils.getObject(getKey());
        if (data != null) {
            load(data.url);
        }
        refresh();
    }

    public void load(String url) {
        webView.loadUrl(url);
    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;data=%s;category=%s", uid, name, category));
    }


    protected void loadData(final boolean first) {
        DataRestful.INSTANCE.get(uid, name, category, new DataRestful.OnDataRestfulGet() {
            @Override
            public void onSuccess(Data data) {
                load(data.url);
                FileUtils.setObject(getKey(), data);
            }

            @Override
            public void onError(RestfulError error) {
                ViewUtils.toast(error.msg);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFinish() {

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
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
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }


}
