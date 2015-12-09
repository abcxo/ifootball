package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.detail.ChatDetailFragment;

import java.util.Stack;

/**
 * Created by shadow on 15/11/4.
 */
public class ChatDetailActivity extends AppCompatActivity {
    private static Stack<String> chatIds = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chat);
        Bundle bundle = getIntent().getExtras();
        long uid = bundle.getLong(Constants.KEY_UID);
        long uid2 = bundle.getLong(Constants.KEY_UID2);
        String chatId = uid + "|" + uid2;
        chatIds.push(chatId);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ChatDetailFragment.newInstance(getIntent().getExtras())).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatIds.pop();
    }

    public static boolean isChatting(long uid, long uid2) {
        try {
            String chatId = uid + "|" + uid2;
            String chatId2 = uid2 + "|" + uid;
            String currentChatId = chatIds.peek();
            return chatId.equals(currentChatId) || chatId2.equals(currentChatId);
        } catch (Exception e) {
            return false;
        }

    }

}
