package com.abcxo.android.ifootball.controllers.fragments.search;

import android.os.Bundle;

import com.abcxo.android.ifootball.controllers.fragments.main.TweetFragment;
import com.abcxo.android.ifootball.restfuls.TweetRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class SearchTweetFragment extends TweetFragment{
    public static SearchTweetFragment newInstance() {
        return newInstance(null);
    }

    public  String keyword="";

    public static SearchTweetFragment newInstance(Bundle args) {
        SearchTweetFragment fragment = new SearchTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public  String getKeyword() {
        return keyword;
    }

    @Override
    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.SEARCH;
    }
}
