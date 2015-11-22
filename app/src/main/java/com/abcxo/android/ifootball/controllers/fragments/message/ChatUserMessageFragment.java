package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.MessageRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class ChatUserMessageFragment extends MessageFragment{
    public static ChatUserMessageFragment newInstance() {
        return newInstance(null);
    }

    public static ChatUserMessageFragment newInstance(Bundle args) {
        ChatUserMessageFragment fragment = new ChatUserMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.CHAT_USER;
    }


}
