package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

import com.abcxo.android.ifootball.restfuls.MessageRestful;

/**
 * Created by shadow on 15/11/5.
 */
public class CommentMessageFragment extends MessageFragment {
    public static CommentMessageFragment newInstance() {
        return newInstance(null);
    }

    public static CommentMessageFragment newInstance(Bundle args) {
        CommentMessageFragment fragment = new CommentMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.COMMENT_TWEET;
    }
}
