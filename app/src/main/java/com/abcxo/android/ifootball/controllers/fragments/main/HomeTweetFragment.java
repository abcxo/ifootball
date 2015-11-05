package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

public class HomeTweetFragment extends TweetFragment {
    public static HomeTweetFragment newInstance() {
        return newInstance(null);
    }

    public static HomeTweetFragment newInstance(Bundle args) {
        HomeTweetFragment fragment = new HomeTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

}
