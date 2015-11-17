package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet implements Parcelable {
    public long id;
    public long uid;


    public int commentCount;
    public int repeatCount;
    public int starCount;


    public String icon;
    public String title;
    public String source;
    public String summary;
    public String text;
    public String cover;
    public String url;
    public String lon;
    public String lat;
    public String images;
    public String time;

    public TweetMainType mainType = TweetMainType.NORMAL;
    public TweetDetailType detailType = TweetDetailType.TWEET;

    public Tweet originTweet;


    public Tweet() {
        super();
    }

    protected Tweet(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        source = in.readString();
        time = in.readString();
        commentCount = in.readInt();
        repeatCount = in.readInt();
        starCount = in.readInt();
        icon = in.readString();
        title = in.readString();
        summary = in.readString();
        text = in.readString();
        cover = in.readString();
        url = in.readString();
        lon = in.readString();
        lat = in.readString();
        images = in.readString();
        originTweet = in.readParcelable(Tweet.class.getClassLoader());
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

    public List<String> imageList() {
        if (!TextUtils.isEmpty(images)) {
            return Arrays.asList(images.split(";"));
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(uid);
        dest.writeString(source);
        dest.writeString(time);
        dest.writeInt(commentCount);
        dest.writeInt(repeatCount);
        dest.writeInt(starCount);
        dest.writeString(icon);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(text);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeString(images);
        dest.writeParcelable(originTweet, flags);
    }


    public enum TweetMainType {

        NORMAL(0),
        TEAM(1),
        NEWS(2),
        SPECIAL(3);
        private int index;

        TweetMainType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetMainType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum TweetDetailType {

        TWEET(0),
        NEWS(1);
        private int index;

        TweetDetailType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetDetailType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }
}
