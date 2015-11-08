package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Message implements Parcelable{
    public String id = "";
    public String uid = "";
    public String icon = "";
    public String name = "";
    public String time = "";
    public String count = "";
    public Content content = new Content();
    public MessageType type = MessageType.NORMAL;
    public Map<String, String> extras = new HashMap<>();

    public Message(){
        super();
    }

    protected Message(Parcel in) {
        id = in.readString();
        uid = in.readString();
        icon = in.readString();
        name = in.readString();
        time = in.readString();
        count = in.readString();
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
        dest.writeString(uid);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(count);
    }
}
