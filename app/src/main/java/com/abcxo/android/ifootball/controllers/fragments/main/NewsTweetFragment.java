package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;

public class NewsTweetFragment extends TweetFragment {

    public static NewsTweetFragment newInstance() {
        return newInstance(null);
    }

    public static NewsTweetFragment newInstance(Bundle args) {
        NewsTweetFragment fragment = new NewsTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

}
