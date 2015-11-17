package com.abcxo.android.ifootball.restfuls;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.google.repacked.apache.commons.io.FileUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class TweetRestful {


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
        tweet.id = 1L;
        tweet.user = UserRestful.INSTANCE.me();
        tweet.source = "新浪微博";
        tweet.time = "3小时前";
        tweet.commentCount = "381";
        tweet.repeatCount = "274";
        tweet.starCount = "96";

        tweet.title = "恒大中超称霸五个连冠";
        tweet.summary = "里皮时代，恒大队的外援威震中超，尤其是孔卡、穆里奇、埃尔克森的南美前场铁三角组合，在2013年横扫亚洲赛场。“恒大靠外援”的标签，在那一年被贴得格外严实，撕都撕不掉。三人的进球，在那一年占了恒大队全队进球的七成。";
        tweet.text = tweet.summary;
        tweet.cover = "http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg";
        tweet.url = "http://www.baidu.com";
        tweet.lon = "0";
        tweet.lat = "0";
        List<String> images = new ArrayList<>();
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931cc7d9ce9efc4b74542a911dc.jpg");
        tweet.images = TextUtils.join(";", images);

        if (getsType == GetsType.TEAM) {
            tweet.mainType = Tweet.TweetMainType.TEAM;
            tweet.detailType = Tweet.TweetDetailType.TWEET;
        } else if (getsType == GetsType.NEWS) {
            tweet.mainType = Tweet.TweetMainType.NEWS;
            tweet.detailType = Tweet.TweetDetailType.NEWS;
        } else {
            tweet.mainType = Tweet.TweetMainType.NORMAL;
            tweet.detailType = Tweet.TweetDetailType.TWEET;
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


    public static TweetRestful INSTANCE = new TweetRestful();

    private TweetService tweetService;

    private TweetRestful() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tweetService = retrofit.create(TweetService.class);
    }


    public interface TweetService {

        @POST("/tweet")
        Call<Tweet> add(@Query("uid") long uid, @Query("prompt") String prompt, @Query("originTid") long originTid, @Body Tweet tweet);

        @Multipart
        @POST("/tweet/photo")
        Call<Tweet> photo(@Query("tid") long tid,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image0,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image1,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image2,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image3,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image4,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image5,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image6,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image7,
                          @Part("image\"; filename=\"image.jpg\" ") RequestBody image8
        );

        @GET("/tweet")
        Call<List<Tweet>> gets(@Query("uid") long uid,
                               @Query("getsType") GetsType type,
                               @Query("pageIndex") int pageIndex,
                               @Query("pageSize") int pageSize);
    }


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


    //添加推文
    public void add(Tweet tweet, final List<Image> images, @NonNull final OnTweetRestfulGet onGet) {
        Call<Tweet> call = tweetService.add(UserRestful.INSTANCE.meId(), null, 0, tweet);
        call.enqueue(new OnRestful<Tweet>() {
            @Override
            void onSuccess(final Tweet tweet) {
                if (images.size() > 0) {
                    List<RequestBody> requestBodies = new ArrayList<RequestBody>();
                    try {
                        for (Image image : images) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byteArrayOutputStream.writeTo(new FileOutputStream(image.url));
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), byteArrayOutputStream.toByteArray());
                            requestBodies.add(requestBody);
                        }
                    } catch (Exception e) {
                    }

                    Call<Tweet> callPhoto;
                    switch (requestBodies.size()) {
                        case 1:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), null, null, null, null, null, null, null, null);
                            break;
                        case 2:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), null, null, null, null, null, null, null);
                            break;
                        case 3:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), null, null, null, null, null, null);
                            break;
                        case 4:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), null, null, null, null, null);
                            break;
                        case 5:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), null, null, null, null);
                            break;
                        case 6:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), requestBodies.get(5), null, null, null);
                            break;
                        case 7:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), requestBodies.get(5), requestBodies.get(6), null, null);
                            break;
                        case 8:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), requestBodies.get(5), requestBodies.get(6), requestBodies.get(7), null);
                            break;
                        case 9:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), requestBodies.get(5), requestBodies.get(6), requestBodies.get(7), requestBodies.get(8));
                            break;
                        default:
                            callPhoto = tweetService.photo(tweet.id, requestBodies.get(0), requestBodies.get(1), requestBodies.get(2), requestBodies.get(3), requestBodies.get(4), requestBodies.get(5), requestBodies.get(6), requestBodies.get(7), requestBodies.get(8));
                            break;
                    }

                    callPhoto.enqueue(new OnRestful<Tweet>() {
                        @Override
                        void onSuccess(Tweet tweet) {
                            onGet.onSuccess(tweet);
                        }

                        @Override
                        void onError(RestfulError error) {
                            //照片上传失败也当成是成功
                            onGet.onSuccess(tweet);
                        }

                        @Override
                        void onFinish() {
                            onGet.onFinish();
                        }
                    });
                } else {
                    onGet.onSuccess(tweet);
                    onGet.onFinish();
                }

            }

            @Override
            void onError(RestfulError error) {
                onGet.onError(error);
                onGet.onFinish();
            }

            @Override
            void onFinish() {
            }
        });
    }


    public enum GetsType {
        HOME(0),
        TEAM(1),
        NEWS(2),
        TWEET(3);
        private int index;

        GetsType(int index) {
            this.index = index;
        }

        public static int size() {
            return GetsType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    //获取推文列表
    public void gets(GetsType getsType, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        gets(UserRestful.INSTANCE.meId(), getsType, pageIndex, onList);
    }

    public void gets(long uid, final GetsType getsType, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        Call<List<Tweet>> call = tweetService.gets(uid, getsType, pageIndex, Constants.PAGE_SIZE);
        call.enqueue(new OnRestful<List<Tweet>>() {
            @Override
            void onSuccess(List<Tweet> tweets) {
                onList.onSuccess(tweets);
            }

            @Override
            void onError(RestfulError error) {
                onList.onError(error);
            }

            @Override
            void onFinish() {
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
