package com.abcxo.android.ifootball.restfuls;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.google.repacked.apache.commons.lang3.StringUtils;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class MessageRestful {
    public static MessageRestful INSTANCE = new MessageRestful();


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
        message.text = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
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


    private MessageService messageService;

    public interface MessageService {
        @GET("/message/list")
        Call<List<Message>> gets(@Query("uid") long uid,
                                 @Query("uid2") long uid2,
                                 @Query("tid") long tid,
                                 @Query("getsType") GetsType type,
                                 @Query("pageIndex") int pageIndex,
                                 @Query("pageSize") int pageSize);

        @POST("/message/chat")
        Call<Object> chat(@Body Message message);

    }


    private MessageRestful() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);
    }


    //主页，球队，新闻，其他用户
    public interface OnMessageRestfulList {
        void onSuccess(List<Message> messages);

        void onError(RestfulError error);

        void onFinish();

    }

    //评论，转发，收藏，删除
    public interface OnMessageRestfulDo {
        void onSuccess();

        void onError(RestfulError error);

        void onFinish();

    }


    //获取Main列表
    public enum GetsType {
        ALL(0),
        CHAT(1),
        CHAT_USER(2),
        COMMENT(3),
        COMMENT_TWEET(4),
        PROMPT(5),
        FOCUS(6),
        STAR(7),
        OTHER(8);

        private int index;

        GetsType(int index) {
            this.index = index;
        }

        public static int size() {
            return GetsType.values().length;
        }

        public int getIndex() {
            return index;
        }

    }


    //获取推文列表
    public void gets(long uid, long uid2, long tid, GetsType getsType, int pageIndex, @NonNull final OnMessageRestfulList onList) {
        Call<List<Message>> call = messageService.gets(uid, uid2, tid, getsType, pageIndex, Constants.PAGE_SIZE);
        call.enqueue(new OnRestful<List<Message>>() {
            @Override
            void onSuccess(List<Message> messages) {
                onList.onSuccess(messages);
            }

            @Override
            void onError(RestfulError error) {
                onList.onError(error);
            }

            @Override
            void onFinish() {
                onList.onFinish();
            }
        });

    }


    public void chat(Message message, @NonNull final OnMessageRestfulDo onDo) {
        Call<Object> call = messageService.chat(message);
        call.enqueue(new OnRestful<Object>() {
            @Override
            void onSuccess(Object o) {
                onDo.onSuccess();
            }

            @Override
            void onError(RestfulError error) {
                onDo.onError(error);
            }

            @Override
            void onFinish() {
                onDo.onFinish();
            }
        });
    }


}
