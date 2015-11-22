package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.SearchAdapter;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentTweetMessageFragment;
import com.abcxo.android.ifootball.databinding.FragmentDetailTweetBinding;
import com.abcxo.android.ifootball.models.Tweet;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetDetailFragment extends DetailFragment {

    private Tweet tweet;

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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDetailTweetBinding binding = DataBindingUtil.bind(view);
        binding.setTweet(tweet);
        binding.setHandler(new BindingHandler());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, tweet.uid);
        bundle.putLong(Constants.KEY_TWEET, tweet.id);
        getChildFragmentManager().beginTransaction().replace(R.id.comment, CommentTweetMessageFragment.newInstance(bundle)).commit();

    }


    public class BindingHandler {
        public void onClickComment(View view) {

        }

        public void onClickSend(View view) {

        }
    }
}
