package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

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
}
