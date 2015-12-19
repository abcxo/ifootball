package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentDetailTweetBinding;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.NetworkUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetDetailFragment extends DetailFragment {

    private Tweet tweet;
    private long tid;
    public FragmentDetailTweetBinding binding;

    public static TweetDetailFragment newInstance() {
        return newInstance(null);
    }

    public static TweetDetailFragment newInstance(Bundle args) {
        TweetDetailFragment fragment = new TweetDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tweet = (Tweet) args.get(Constants.KEY_TWEET);
            tid = args.getLong(Constants.KEY_TID);

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tweet, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        if (tweet != null) {
            bindData(view);
        } else {
            ViewUtils.loading(getActivity());
            TweetRestful.INSTANCE.get(tid, new TweetRestful.OnTweetRestfulGet() {
                @Override
                public void onSuccess(Tweet tweet) {
                    TweetDetailFragment.this.tweet = tweet;
                    bindData(view);
                }

                @Override
                public void onError(RestfulError error) {
                    ViewUtils.toast(error.msg);
                }

                @Override
                public void onFinish() {
                    ViewUtils.dismiss();
                }
            });

        }

    }


    public void bindData(View view) {
        binding.setTweet(tweet);
        final WebView webView = (WebView) view.findViewById(R.id.webview);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(tweet.content);
            }
        };
        refreshLayout.setOnRefreshListener(listener);

        if (!TextUtils.isEmpty(tweet.content)) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    listener.onRefresh();
                }
            });
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "handler");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                try {
                    OkHttpClient mOkHttpClient = NetworkUtils.getClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = mOkHttpClient.newCall(request);
                    Response response = call.execute();
                    String contentType = response.header("Content-Type");
                    String encodingType = "UTF-8";
                    InputStream inputStream = response.body().byteStream();
                    return new WebResourceResponse(contentType, encodingType, inputStream);

                } catch (Exception e) {

                }
                return null;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);

            }
        });

    }

    @JavascriptInterface
    public void onImageClick(String url) {
        NavUtils.toImage(getActivity(), (ArrayList<Image>) tweet.imageList(), tweet.indexOfImage(url));
    }

}
