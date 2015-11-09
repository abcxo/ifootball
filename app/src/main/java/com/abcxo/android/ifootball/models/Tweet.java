package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet implements Parcelable {
    public String id;
    public User user;
    public String source;
    public String time;
    public String commentCount;
    public String repeatCount;
    public String starCount;
    public Content content;
    public Tweet originTweet;
    public TweetMainType mainType = TweetMainType.NORMAL;
    public TweetDetailType detailType = TweetDetailType.TWEET;

    public Tweet() {
        super();
    }


    protected Tweet(Parcel in) {
        id = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        source = in.readString();
        time = in.readString();
        commentCount = in.readString();
        repeatCount = in.readString();
        starCount = in.readString();
        content = in.readParcelable(Content.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(user, flags);
        dest.writeString(source);
        dest.writeString(time);
        dest.writeString(commentCount);
        dest.writeString(repeatCount);
        dest.writeString(starCount);
        dest.writeParcelable(content, flags);
        dest.writeParcelable(originTweet, flags);
    }
}
