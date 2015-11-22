package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.MessageRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class StarMessageFragment extends MessageFragment {
    public static StarMessageFragment newInstance() {
        return newInstance(null);
    }

    public static StarMessageFragment newInstance(Bundle args) {
        StarMessageFragment fragment = new StarMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.STAR;
    }
}
