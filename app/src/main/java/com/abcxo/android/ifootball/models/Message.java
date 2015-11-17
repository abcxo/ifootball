package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Message implements Parcelable {
    public long id;
    public long uid;

    public String icon;
    public String title;
    public String summary;
    public String text;
    public String cover;
    public String url;
    public String lon;
    public String lat;
    public String images;
    public String time;

    public MessageType messageType = MessageType.NORMAL;

    public Message() {
        super();
    }

    protected Message(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        icon = in.readString();
        title = in.readString();
        summary = in.readString();
        text = in.readString();
        cover = in.readString();
        url = in.readString();
        lon = in.readString();
        lat = in.readString();
        images = in.readString();
        time = in.readString();
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
        dest.writeString(icon);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(text);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeString(images);
        dest.writeString(time);
    }



    public enum MessageType {

        NORMAL(0),
        FOCUS(1),
        COMMENT(2),
        PROMPT(3),
        STAR(4),
        CHAT(5),
        SPECIAL(6);

        private int index;

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

}
