package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class HomeTweetFragment extends TweetFragment {
    public static HomeTweetFragment newInstance() {
        return newInstance(null);
    }

    public static HomeTweetFragment newInstance(Bundle args) {
        HomeTweetFragment fragment = new HomeTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TweetRestful.GetsType getGetsType(){
        return TweetRestful.GetsType.HOME;
    }

}
