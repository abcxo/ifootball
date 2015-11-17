package com.abcxo.android.ifootball.restfuls;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;
import com.google.repacked.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class MessageRestful {
    public static MessageRestful INSTANCE = new MessageRestful();

    private MessageRestful() {
    }

    /**
     * 测试
     */

    private void post(Runnable runnable) {
        new Handler().postDelayed(runnable, 2000);
    }

    private Message testMessage() {
        Message message = new Message();
        message.id = 1L;
        message.uid = 1;
        message.time = "3小时前";

        message.title = "恒大中超称霸五个连冠";
        message.summary = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
        message.text = message.summary;
        message.cover = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
        message.url = "http://www.baidu.com";
        message.lon = "0";
        message.lat = "0";
        List<String> images = new ArrayList<>();
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        message.images = StringUtils.join(images, ";");

        message.messageType = Message.MessageType.NORMAL;

        return message;
    }

    public List<Message> testMesages() {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < Constants.PAGE_SIZE; i++) {
            messages.add(testMessage());
        }
        return messages;
    }


    //主页，球队，新闻，其他用户
    public interface OnMessageRestfulList {
        void onSuccess(List<Message> messages);

        void onError(RestfulError error);

        void onFinish();

    }


    //获取Main列表
    public enum GetsType {
        ALL,
        CHAT,
        COMMENT,
        PROMPT,
        FOCUS,
        STAR,
        OTHER,
    }


    //获取推文列表
    public void getMessages(GetsType getsType, int pageIndex, @NonNull final OnMessageRestfulList onList) {

    }



}
