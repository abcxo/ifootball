package com.abcxo.android.ifootball.controllers.fragments.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.restfuls.MessageRestful;

import java.util.List;

/**
 * Created by shadow on 15/11/5.
 */
public class ChatUserMessageFragment extends MessageFragment {
    private BroadcastReceiver receiver;

    public static ChatUserMessageFragment newInstance() {
        return newInstance(null);
    }

    public static ChatUserMessageFragment newInstance(Bundle args) {
        ChatUserMessageFragment fragment = new ChatUserMessageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Message message = intent.getParcelableExtra("message");
                if ((message.uid == uid && message.uid2 == uid2) || (message.uid == uid2 && message.uid2 == uid)) {
                    addMessage(message);

                }
            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_MESSAGE));
    }

    @Override
    public void addMessage(Message message) {
        super.addMessage(message);
        recyclerView.getRecyclerView().smoothScrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void addMessages(List<Message> messages) {
        super.addMessages(messages);
        recyclerView.getRecyclerView().smoothScrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void refreshMessages(List<Message> messages) {
        super.refreshMessages(messages);
        recyclerView.getRecyclerView().smoothScrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.CHAT_USER;
    }


}
