package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.MessageRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class FocusMessageFragment extends MessageFragment{
    public static FocusMessageFragment newInstance() {
        return newInstance(null);
    }

    public static FocusMessageFragment newInstance(Bundle args) {
        FocusMessageFragment fragment = new FocusMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.FOCUS;
    }
}
