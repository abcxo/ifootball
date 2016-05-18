package com.abcxo.android.ifootball.models;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.restfuls.MessageRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.io.Serializable;

/**
 * Created by SHARON on 15/10/29.
 */
public class Message implements Parcelable, Serializable {
    public long id;

    //用户id
    public long uid;
    public long uid2;
    public long tid;

    public String icon;
    public String icon2;
    public String title;
    public String content;
    public String time;
    public MessageType messageType = MessageType.NORMAL;

    public MessageMainType mainType = MessageMainType.NORMAL;
    public MessageDetailType detailType = MessageDetailType.NORMAL;

    private transient BindingHandler handler = new BindingHandler();

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }

    public Message() {
        super();
    }

    protected Message(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        uid2 = in.readLong();
        tid = in.readLong();
        icon = in.readString();
        icon2 = in.readString();
        title = in.readString();
        content = in.readString();
        time = in.readString();
        messageType = MessageType.valueOf(in.readString());
        mainType = MessageMainType.valueOf(in.readString());
        detailType = MessageDetailType.valueOf(in.readString());
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(uid);
        dest.writeLong(uid2);
        dest.writeLong(tid);
        dest.writeString(icon);
        dest.writeString(icon2);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeString(messageType.name());
        dest.writeString(mainType.name());
        dest.writeString(detailType.name());
    }


    public enum MessageType {

        NORMAL(0),
        FOCUS(1),
        COMMENT(2),
        PROMPT(3),
        STAR(4),
        CHAT(5),
        CHAT_GROUP(6),
        SPECIAL(7);

        public int index;

        MessageType(int index) {
            this.index = index;
        }

        public static int size() {
            return MessageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    public enum MessageMainType {

        NORMAL(0),
        FOCUS(1),
        COMMENT(2),
        PROMPT(3),
        STAR(4),
        CHAT_GROUP(5),
        SPECIAL(6),
        COMMENT_TWEET(7),
        CHAT_USER(8),
        CHAT_ME(9);

        public int index;

        MessageMainType(int index) {
            this.index = index;
        }

        public static int size() {
            return MessageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum MessageDetailType {


        NORMAL(0),
        USER(1),
        TWEET(2),
        COMMENT(3),
        CHAT(4),
        NONE(5);
        public int index;

        MessageDetailType(int index) {
            this.index = index;
        }

        public static int size() {
            return MessageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public class BindingHandler {
        public void onClickUser(View view) {
            NavUtils.toUserDetail(view.getContext(), uid);
        }

        public void onClickMessage(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                if (detailType == MessageDetailType.NORMAL) {
                } else if (detailType == MessageDetailType.USER) {
                    NavUtils.toUserDetail(view.getContext(), uid);
                } else if (detailType == MessageDetailType.TWEET) {
                    NavUtils.toTweetDetail(view.getContext(), tid);
                } else if (detailType == MessageDetailType.COMMENT) {
                } else if (detailType == MessageDetailType.CHAT) {
                    NavUtils.toChatDetail(view.getContext(), uid, uid2);//因为都是用户发我的，所以第一个是用户
                }
            } else {
                NavUtils.toSign(view.getContext());
            }
        }
    }
}
