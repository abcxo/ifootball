package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

public class TeamTweetFragment extends TweetFragment {

    public static TeamTweetFragment newInstance() {
        return newInstance(null);
    }

    public static TeamTweetFragment newInstance(Bundle args) {
        TeamTweetFragment fragment = new TeamTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

}
