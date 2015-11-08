package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class TeamTweetFragment extends TweetFragment {

    public static TeamTweetFragment newInstance() {
        return newInstance(null);
    }

    public static TeamTweetFragment newInstance(Bundle args) {
        TeamTweetFragment fragment = new TeamTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TweetRestful.GetsType getGetsType(){
        return TweetRestful.GetsType.TEAM;
    }

}
