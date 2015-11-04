package com.abcxo.android.ifootball.controllers.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.adapters.TweetAdapter;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;

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

        adapter = new TweetAdapter(list);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetRestful.INSTNCE.getMainTweets(TweetRestful.GetsType.TWEET, 0, new TweetRestful.OnTweetRestfulList() {
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


}
