package com.abcxo.android.ifootball.controllers.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.adapters.TweetRecyclerViewAdapter;
import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class TweetFragment extends Fragment {
    public static HomeFragment newInstance() {
        return newInstance(null);
    }

    public static HomeFragment newInstance(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.home_refresh);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.home_recycler);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TweetRecyclerViewAdapter(TweetRestful.getInstance().getHomeTweets()));
        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 5000);

            }
        });
    }

}
