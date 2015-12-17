package com.abcxo.android.ifootball.restfuls;

import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.Tweet;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class TweetRestful {

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

        @POST(Constants.PATH+"/tweet")
        Call<Tweet> add(@Query("prompt") String prompt, @Query("originTid") long originTid, @Body Tweet tweet);

        @Multipart
        @POST(Constants.PATH+"/tweet/photo")
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

        @GET(Constants.PATH+"/tweet/list")
        Call<List<Tweet>> gets(@Query("getsType") GetsType type,
                               @Query("uid") long uid,
                               @Query("keyword") String keyword,
                               @Query("pageIndex") int pageIndex,
                               @Query("pageSize") int pageSize);


        @GET(Constants.PATH+"/tweet")
        Call<Tweet> get(@Query("uid") long uid, @Query("tid") long tid);


        @POST(Constants.PATH+"/tweet/star")
        Call<Object> star(@Query("uid") long uid, @Query("tid") long tid, @Query("star") boolean star);

        @POST(Constants.PATH+"/tweet/comment")
        Call<Object> comment(@Body Message message);

        @DELETE(Constants.PATH+"/tweet")
        Call<Object> delete(@Query("tid") long tid);
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
    public void add(Tweet tweet, final List<Image> images, String prompt, long originTid, @NonNull final OnTweetRestfulGet onGet) {
        Call<Tweet> call = tweetService.add(prompt != null ? prompt : "", originTid, tweet);
        call.enqueue(new OnRestful<Tweet>() {
            @Override
            void onSuccess(final Tweet tweet) {
                if (images != null && images.size() > 0) {
                    List<RequestBody> requestBodies = new ArrayList<RequestBody>();
                    try {
                        for (Image image : images) {
                            File file = new File(image.url);
                            byte[] bytes = new byte[(int) file.length()];
                            FileInputStream fileInputStream = new FileInputStream(file);
                            fileInputStream.read(bytes);
                            fileInputStream.close();
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
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


    //获取单个用户
    public void get(long tid, @NonNull final OnTweetRestfulGet onGet) {
        Call<Tweet> call = tweetService.get(UserRestful.INSTANCE.meId(), tid);
        call.enqueue(new OnRestful<Tweet>() {
            @Override
            void onSuccess(Tweet tweet) {
                onGet.onSuccess(tweet);
            }

            @Override
            void onError(RestfulError error) {
                onGet.onError(error);
            }

            @Override
            void onFinish() {
                onGet.onFinish();
            }
        });
    }

    public void delete(long tid, @NonNull final OnTweetRestfulDo onDo) {
        Call<Object> call = tweetService.delete(tid);
        call.enqueue(new OnRestful<Object>() {
            @Override
            void onSuccess(Object object) {
                onDo.onSuccess();
            }

            @Override
            void onError(RestfulError error) {
                onDo.onError(error);
            }

            @Override
            void onFinish() {
                onDo.onFinish();
            }
        });
    }

    public enum GetsType {
        HOME(0),
        TEAM(1),
        NEWS(2),
        USER(3),
        SEARCH(4);
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


    public void gets(final GetsType getsType, long uid, String keyword, int pageIndex, @NonNull final OnTweetRestfulList onList) {
        Call<List<Tweet>> call = tweetService.gets(getsType, uid, keyword, pageIndex, Constants.PAGE_SIZE);
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
    }


    public void star(long tid, boolean isStar, @NonNull final OnTweetRestfulDo onDo) {
        Call<Object> call = tweetService.star(UserRestful.INSTANCE.meId(), tid, isStar);
        call.enqueue(new OnRestful<Object>() {
            @Override
            void onSuccess(Object o) {
                onDo.onSuccess();
            }

            @Override
            void onError(RestfulError error) {
                onDo.onError(error);
            }

            @Override
            void onFinish() {
                onDo.onFinish();
            }
        });

    }


    public void comment(Message message, @NonNull final OnTweetRestfulDo onDo) {
        Call<Object> call = tweetService.comment(message);
        call.enqueue(new OnRestful<Object>() {
            @Override
            void onSuccess(Object o) {
                onDo.onSuccess();
            }

            @Override
            void onError(RestfulError error) {
                onDo.onError(error);
            }

            @Override
            void onFinish() {
                onDo.onFinish();
            }
        });
    }

}
