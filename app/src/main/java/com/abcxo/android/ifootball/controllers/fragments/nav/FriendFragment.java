package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class FriendFragment extends NavFragment {

    public static FriendFragment newInstance() {
        return newInstance(null);
    }

    public static FriendFragment newInstance(Bundle args) {
        FriendFragment fragment = new FriendFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }
    @Override
    public int getToolbarResId() {
        return R.id.friend_toolbar;
    }
}
