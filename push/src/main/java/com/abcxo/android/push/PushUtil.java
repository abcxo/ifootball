package com.abcxo.android.push;

import android.content.Context;
import android.content.Intent;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONObject;

/**
 * Created by shadow on 15/12/6.
 */
public class PushUtil {
    private static String messageId;

    public static void enable(Context context) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                if (!uMessage.message_id.equals(messageId)) {//为了排除推送两次的bug
                    messageId = uMessage.message_id;
                    Intent intent = new Intent(PushConstants.ACTION_PUSH);
                    JSONObject object = new JSONObject(uMessage.extra);
                    intent.putExtra(PushConstants.ACTION_PUSH_OBJECT, object.toString());
                    context.sendBroadcast(intent);
                }

            }
        };
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void launchApp(Context context, UMessage uMessage) {
//                super.launchApp(context, uMessage);
                Intent intent = new Intent(PushConstants.ACTION_PUSH_CLICK);
                JSONObject object = new JSONObject(uMessage.extra);
                intent.putExtra(PushConstants.ACTION_PUSH_OBJECT, object.toString());
                context.sendBroadcast(intent);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.enable();
    }
}
