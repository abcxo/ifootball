package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

/**
 * Created by shadow on 15/11/5.
 */
public class PromptMessageFragment extends MessageFragment{
    public static PromptMessageFragment newInstance() {
        return newInstance(null);
    }

    public static PromptMessageFragment newInstance(Bundle args) {
        PromptMessageFragment fragment = new PromptMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }
}
