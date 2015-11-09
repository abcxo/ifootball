package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Message implements Parcelable {
    public String id;
    public User user;
    public String time;
    public String count;
    public Content content;
    public MessageType type = MessageType.NORMAL;

    public Message() {
        super();
    }


    protected Message(Parcel in) {
        id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        time = in.readString();
        count = in.readString();
        content = in.readParcelable(Content.class.getClassLoader());
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
        dest.writeString(id);
        dest.writeParcelable(user, flags);
        dest.writeString(time);
        dest.writeString(count);
        dest.writeParcelable(content, flags);
    }
}
