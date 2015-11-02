package com.abcxo.android.ifootball.restfuls;

import com.abcxo.android.ifootball.models.Content;
import com.abcxo.android.ifootball.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class TweetRestful {
    private static TweetRestful ourInstance = new TweetRestful();

    public static TweetRestful getInstance() {
        return ourInstance;
    }

    private TweetRestful() {
    }


    public List<Tweet> getHomeTweets(){
        List<Tweet> tweets = new ArrayList<>();
        for (int i= 0;i<100;i++){
            Tweet tweet = new Tweet();
            tweet.id = ""+i;
            tweet.uid=  ""+i;
            tweet.icon = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
            tweet.repeatCount = "100";
            tweet.replyCount = "20";
            tweet.starCount = "596";
            Content content = new Content();
            content.title = "咸蛋超人踢足球";
            content.summary = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
            content.text = content.summary;
            List<String> images = new ArrayList<>();
            images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
            content.images = images;
            tweet.content = content;
            tweet.time = "3个小时";
            tweet.type=0;
            tweets.add(tweet);

        }

        return tweets;
    }
}
