package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet implements Parcelable{
    public String id = "";
    public String uid = "";
    public String icon = "";
    public String name = "";
    public String source = "";
    public String time = "";
    public String commentCount = "";
    public String repeatCount = "";
    public String starCount = "";
    public Content content = new Content();
    public TweetMainType mainType = TweetMainType.NORMAL;
    public TweetDetailType detailType = TweetDetailType.TWEET;
    public Map<String, String> extras = new HashMap<>();

    public Tweet(){
        super();
    }

    protected Tweet(Parcel in) {
        id = in.readString();
        uid = in.readString();
        icon = in.readString();
        name = in.readString();
        source = in.readString();
        time = in.readString();
        commentCount = in.readString();
        repeatCount = in.readString();
        starCount = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
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
        dest.writeString(source);
        dest.writeString(time);
        dest.writeString(commentCount);
        dest.writeString(repeatCount);
        dest.writeString(starCount);
    }
}
