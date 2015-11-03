package com.abcxo.android.ifootball.restfuls;

import com.abcxo.android.ifootball.models.Content;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.TweetType;

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
            private String id;
            private String uid;
            private String icon;
            private String name;
            private String source;
            private Content content;
            private String time;
            private String starCount;
            private String  repeatCount;
            private String replyCount;
            private TweetType type;

            Tweet tweet = new Tweet();
            tweet.setId("" + i);
            tweet.setUid("" + i);
            tweet.setIcon("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
            tweet.setRepeatCount("100");
            tweet.setReplyCount("275");
            tweet.setStarCount("598");
            tweet.setName("咸蛋超人");

            Content content = new Content();
            content.setTitle("咸蛋超人踢足球");
            content.setSummary("里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。");
            content.setText(content.getSummary());

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
