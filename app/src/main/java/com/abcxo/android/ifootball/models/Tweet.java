package com.abcxo.android.ifootball.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet {
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
    public TweetMainType mainType = TweetMainType.TWEET;
    public TweetDetailType detailType = TweetDetailType.TWEET;
    public Map<String, String> extras = new HashMap<>();

}
