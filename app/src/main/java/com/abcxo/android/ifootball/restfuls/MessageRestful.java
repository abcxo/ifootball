package com.abcxo.android.ifootball.restfuls;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.RestfulConstants;
import com.abcxo.android.ifootball.models.Content;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class MessageRestful {
    public static MessageRestful INSTNCE = new MessageRestful();

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
        message.id = "1";
        message.uid = "1";
        message.icon = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
        message.name = "咸蛋超人";
        message.time = "3小时前";
        message.count = "2";

        Content content = new Content();
        content.id = "1";
        content.title = "恒大中超称霸五个连冠";
        content.summary = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
        content.text = content.summary;
        content.cover = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
        content.url = "http://www.baidu.com";
        content.lon = "0";
        content.lat = "0";
        List<String> images = new ArrayList<>();
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        content.images = images;


        message.content = content;
        message.type = MessageType.NORMAL;

        return message;
    }

    public List<Message> testMesages() {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < RestfulConstants.PAGE_SIZE; i++) {
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
        getTweets(UserRestful.INSTANCE.uid, getsType, pageIndex, onList);
    }

    public void getTweets(String uid, GetsType getsType, int pageIndex, @NonNull final OnMessageRestfulList onList) {
        post(new Runnable() {
            @Override
            public void run() {
                onList.onSuccess(testMesages());
                onList.onFinish();
            }
        });
    }

}
