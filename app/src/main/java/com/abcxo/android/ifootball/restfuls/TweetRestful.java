package com.abcxo.android.ifootball.restfuls;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Content;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.TweetDetailType;
import com.abcxo.android.ifootball.models.TweetMainType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class TweetRestful {


    public static TweetRestful INSTNCE = new TweetRestful();

    private TweetRestful() {
    }

    /**
     * 测试
     */

    private void post(Runnable runnable) {
        new Handler().postDelayed(runnable, 2000);
    }

    private Tweet testTweet(GetsType getsType) {

        Tweet tweet = testTweetContent(getsType);
        tweet.originTweet = testTweetContent(getsType);
        return tweet;
    }


    private Tweet testTweetContent(GetsType getsType) {

        Tweet tweet = new Tweet();
        tweet.id = "1";
        tweet.user = UserRestful.INSTANCE.me();
        tweet.source = "新浪微博";
        tweet.time = "3小时前";
        tweet.commentCount = "381";
        tweet.repeatCount = "274";
        tweet.starCount = "96";

        Content content = new Content();
        content.id = "1";
        content.title = "恒大中超称霸五个连冠";
        content.summary = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
        content.text = content.summary;
        content.cover = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
        content.url = "http://www.baidu.com";
        content.lon = "0";
        content.lat = "0";
        List<String> images = new ArrayList<>();
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        content.images = images;


        tweet.content = content;

        if (getsType == GetsType.TEAM) {
            tweet.mainType = TweetMainType.TEAM;
            tweet.detailType = TweetDetailType.TWEET;
        } else if (getsType == GetsType.NEWS) {
            tweet.mainType = TweetMainType.NEWS;
            tweet.detailType = TweetDetailType.NEWS;
        } else {
            tweet.mainType = TweetMainType.NORMAL;
            tweet.detailType = TweetDetailType.TWEET;
        }
        return tweet;
    }


    public List<Tweet> testTweets(GetsType getsType) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < Constants.PAGE_SIZE; i++) {
            tweets.add(testTweet(getsType));
        }
        return tweets;
    }

    /**
     * 测试
     */

    //获取单条微博
    public interface OnTweetRestfulGet {
        void onSuccess(Tweet tweet);

        void onError(RestfulError error);

        void onFinish();

    }

    //评论，转发，收藏，删除
    public interface OnTweetRestfulDo {
        void onSuccess();

        void onError(RestfulError error);

        void onFinish();

    }

    //主页，球队，新闻，其他用户
    public interface OnTweetRestfulList {
        void onSuccess(List<Tweet> tweets);

        void onError(RestfulError error);

        void onFinish();

    }

    //获取单条推文
    public void getTweet(String tid, @NonNull final OnTweetRestfulGet onGet) {
        post(new Runnable() {
            @Override
            public void run() {
                onGet.onSuccess(testTweet(GetsType.TWEET));
                onGet.onFinish();
            }
        });
    }


    //获取Main列表
    public enum GetsType {
        TWEET,
        TEAM,
        NEWS,
    }

    public void getMainTweets(GetsType getsType, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        getTweets(UserRestful.INSTANCE.meId(), getsType, pageIndex, onList);
    }

    //获取推文列表
    public void getTweets(GetsType getsType, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        getTweets(UserRestful.INSTANCE.meId(), getsType, pageIndex, onList);
    }

    public void getTweets(String uid, final GetsType getsType, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        post(new Runnable() {
            @Override
            public void run() {
                onList.onSuccess(testTweets(getsType));
                onList.onFinish();
            }
        });
    }

    //搜索推文列表
    public void searchTweets(String keyword, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        post(new Runnable() {
            @Override
            public void run() {
                onList.onSuccess(testTweets(GetsType.TWEET));
                onList.onFinish();
            }
        });
    }


}
