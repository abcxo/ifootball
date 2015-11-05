package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

/**
 * Created by shadow on 15/11/5.
 */
public class ChatMessageFragment extends MessageFragment{
    public static ChatMessageFragment newInstance() {
        return newInstance(null);
    }

    public static ChatMessageFragment newInstance(Bundle args) {
        ChatMessageFragment fragment = new ChatMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }
}
