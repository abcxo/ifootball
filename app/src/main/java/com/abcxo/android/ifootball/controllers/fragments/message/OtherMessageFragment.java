package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.MessageRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class OtherMessageFragment extends MessageFragment{
    public static OtherMessageFragment newInstance() {
        return newInstance(null);
    }

    public static OtherMessageFragment newInstance(Bundle args) {
        OtherMessageFragment fragment = new OtherMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.OTHER;
    }
}
