package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class VideoTweetFragment extends TweetFragment {

    public static VideoTweetFragment newInstance() {
        return newInstance(null);
    }

    public static VideoTweetFragment newInstance(Bundle args) {
        VideoTweetFragment fragment = new VideoTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TweetRestful.GetsType getGetsType(){
        return TweetRestful.GetsType.VIDEO;
    }


}
