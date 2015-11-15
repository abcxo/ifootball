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
    public String id;
    public User user;
    public String time;
    public String count;
    public MessageType type = MessageType.NORMAL;

    public String title;
    public String summary;
    public String text;
    public String cover;
    public String url;
    public String lon;
    public String lat;
    public String images;

    public Message() {
        super();
    }


    protected Message(Parcel in) {
        id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        time = in.readString();
        count = in.readString();
        title = in.readString();
        summary = in.readString();
        text = in.readString();
        cover = in.readString();
        url = in.readString();
        lon = in.readString();
        lat = in.readString();
        images = in.readString();
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
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(text);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeString(images);
    }
}
