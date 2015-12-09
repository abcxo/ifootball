package com.abcxo.android.ifootball.controllers.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.push.PushConstants;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by shadow on 15/12/6.
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = PushReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String object = intent.getStringExtra(PushConstants.ACTION_PUSH_OBJECT);
            JSONObject o = new JSONObject(object);
            String m = o.optString("message");
            Message message = new Gson().fromJson(m, Message.class);
            if (PushConstants.ACTION_PUSH.equals(intent.getAction())) {
                Intent i = new Intent(Constants.ACTION_MESSAGE);
                i.putExtra("message", message);
                LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(i);
            } else if (PushConstants.ACTION_PUSH_CLICK.equals(intent.getAction())) {
                Intent i = new Intent(Constants.ACTION_MESSAGE_CLICK);
                i.putExtra("message", message);
                LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
