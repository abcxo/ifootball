package com.abcxo.android.ifootball.controllers.fragments.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.NewsDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.TweetDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.UserDetailActivity;
import com.abcxo.android.ifootball.controllers.adapters.TweetAdapter;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TweetFragment extends Fragment {


    protected List<Tweet> list = new ArrayList<>();

    protected SwipeRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;
    protected TweetAdapter adapter;

    public static TweetFragment newInstance() {
        return newInstance(null);
    }

    public static TweetFragment newInstance(Bundle args) {
        TweetFragment fragment = new TweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new TweetAdapter(list, new Handler());
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetRestful.INSTNCE.getMainTweets(getGetsType(), 0, new TweetRestful.OnTweetRestfulList() {
                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        refreshTweets(tweets);
                    }

                    @Override
                    public void onError(RestfulError error) {

                    }

                    @Override
                    public void onFinish() {
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        };
        refreshLayout.setOnRefreshListener(listener);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });


    }


    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.TWEET;
    }


    protected void refreshTweets(List<Tweet> tweets) {
        list.clear();
        list.addAll(tweets);
        adapter.notifyDataSetChanged();
    }

    protected void addTweets(List<Tweet> tweets) {
        int bCount = list.size();
        list.addAll(tweets);
        adapter.notifyItemRangeInserted(bCount, tweets.size());
    }


    public class Handler {

        public void onClickUser(View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            Tweet tweet = (Tweet) binding.getRoot().getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KEY_USER, tweet.user);
            Intent intent = new Intent(getActivity(), UserDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }


        public void onClickTweet(View view) {
            onClickItem(view, TweetDetailActivity.class);
        }

        public void onClickNews(View view) {

            onClickItem(view, NewsDetailActivity.class);
        }

        public void onClickItem(View view, Class<? extends Activity> activity) {
            ViewDataBinding binding = DataBindingUtil.getBinding(view);
            Tweet tweet = (Tweet) binding.getRoot().getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KEY_TWEET, tweet);
            Intent intent = new Intent(getActivity(), activity);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

}
