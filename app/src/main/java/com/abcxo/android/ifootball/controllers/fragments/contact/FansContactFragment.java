package com.abcxo.android.ifootball.controllers.fragments.contact;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class FansContactFragment extends ContactUserFragment {
    public static FansContactFragment newInstance() {
        return newInstance(null);
    }

    public static FansContactFragment newInstance(Bundle args) {
        FansContactFragment fragment = new FansContactFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UserRestful.GetsType getGetsType() {
        return UserRestful.GetsType.FANS;
    }
}
