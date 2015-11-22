package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.os.Bundle;

import com.abcxo.android.ifootball.controllers.fragments.main.TweetFragment;
import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class UserTweetFragment extends TweetFragment {

    public static UserTweetFragment newInstance() {
        return newInstance(null);
    }

    public static UserTweetFragment newInstance(Bundle args) {
        UserTweetFragment fragment = new UserTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TweetRestful.GetsType getGetsType(){
        return TweetRestful.GetsType.USER;
    }

}
