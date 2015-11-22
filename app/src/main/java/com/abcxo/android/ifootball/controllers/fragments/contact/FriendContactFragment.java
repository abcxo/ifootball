package com.abcxo.android.ifootball.controllers.fragments.contact;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class FriendContactFragment extends ContactUserFragment {
    public static FriendContactFragment newInstance() {
        return newInstance(null);
    }

    public static FriendContactFragment newInstance(Bundle args) {
        FriendContactFragment fragment = new FriendContactFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UserRestful.GetsType getGetsType() {
        return UserRestful.GetsType.FRIEND;
    }
}
