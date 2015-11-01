package com.abcxo.android.ifootball.models;

import java.util.List;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet {
    public String id;
    public String uid;
    public String icon;

    public String source;
    public List<Content> contents;

    public String time;

    public int goodCount;
    public int forwardCount;
    public int replyCount;
    public TweetType tweetType;


    enum TweetType{
        USER,
        TEAM,
        NEWS
    }
}
