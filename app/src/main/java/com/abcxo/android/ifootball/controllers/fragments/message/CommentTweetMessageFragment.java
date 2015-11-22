package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;

/**
 * Created by shadow on 15/11/5.
 */
public class CommentTweetMessageFragment extends MessageFragment{
    public static CommentTweetMessageFragment newInstance() {
        return newInstance(null);
    }

    public static CommentTweetMessageFragment newInstance(Bundle args) {
        CommentTweetMessageFragment fragment = new CommentTweetMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }
}
