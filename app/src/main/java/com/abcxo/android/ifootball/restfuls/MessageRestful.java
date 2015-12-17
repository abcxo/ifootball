package com.abcxo.android.ifootball.restfuls;

import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class MessageRestful {
    public static MessageRestful INSTANCE = new MessageRestful();
    private MessageService messageService;

    public interface MessageService {
        @GET(Constants.PATH+"/message/list")
        Call<List<Message>> gets(@Query("getsType") GetsType type,
                                 @Query("uid") long uid,
                                 @Query("uid2") long uid2,
                                 @Query("tid") long tid,
                                 @Query("pageIndex") int pageIndex,
                                 @Query("pageSize") int pageSize);

        @POST(Constants.PATH+"/message/chat")
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
    public void gets(final GetsType getsType, long uid, long uid2, long tid, int pageIndex, @NonNull final OnMessageRestfulList onList) {
        Call<List<Message>> call = messageService.gets(getsType, uid, uid2, tid, pageIndex, Constants.PAGE_SIZE);
        call.enqueue(new OnRestful<List<Message>>() {
            @Override
            void onSuccess(List<Message> messages) {
                if (messages != null) {
                    for (Message message : messages) {
                        if (getsType == GetsType.COMMENT_TWEET) {
                            message.mainType = Message.MessageMainType.COMMENT_TWEET;
                            message.detailType = Message.MessageDetailType.COMMENT;
                        } else if (getsType == GetsType.CHAT_USER) {
                            message.mainType = Message.MessageMainType.CHAT_USER;
                            message.detailType = Message.MessageDetailType.NONE;
                        } else {
                            Message.MessageMainType mainType = Message.MessageMainType.valueOf(message.messageType.name());
                            if (mainType == Message.MessageMainType.FOCUS) {
                                message.detailType = Message.MessageDetailType.USER;
                            } else if (mainType == Message.MessageMainType.COMMENT ||
                                    mainType == Message.MessageMainType.PROMPT ||
                                    mainType == Message.MessageMainType.STAR) {
                                message.detailType = Message.MessageDetailType.TWEET;
                            } else if (mainType == Message.MessageMainType.CHAT) {
                                message.detailType = Message.MessageDetailType.CHAT;
                            } else {
                                message.detailType = Message.MessageDetailType.NORMAL;
                            }
                            message.mainType = mainType;
                        }
                    }
                }
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
