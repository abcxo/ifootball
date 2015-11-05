package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

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
}
