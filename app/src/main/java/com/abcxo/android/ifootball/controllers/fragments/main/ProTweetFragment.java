package com.abcxo.android.ifootball.controllers.fragments.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.restfuls.TweetRestful;

public class ProTweetFragment extends TweetFragment {
    private BroadcastReceiver receiver;

    public static ProTweetFragment newInstance() {
        return newInstance(null);
    }

    public static ProTweetFragment newInstance(Bundle args) {
        ProTweetFragment fragment = new ProTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh();
            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_REFRESH_PRO));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.PRO;
    }
}
