package com.abcxo.android.ifootball.restfuls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.umeng.message.UmengRegistrar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class UserRestful {

    public static UserRestful INSTANCE = new UserRestful();


    private User user;


    private UserService userService;

    public interface UserService {
        @GET("/user/login")
        Call<User> login(@Query("email") String email, @Query("password") String password, @Query("deviceToken") String deviceToken);

        @GET("/user/logout")
        Call<Object> logout(@Query("uid") long uid);

        @POST("/user/register")
        Call<User> register(@Query("email") String email, @Query("password") String password, @Query("deviceToken") String deviceToken);

        @POST("/user/loginsso")
        Call<User> loginsso(@Query("email") String email, @Query("password") String password,
                            @Query("name") String name, @Query("avatar") String avatar,
                            @Query("gender") User.GenderType gender,
                            @Query("deviceToken") String deviceToken);


        @PUT("/user")
        Call<User> edit(@Body User user);

        @Multipart
        @POST("/user/avatar")
        Call<User> avatar(@Query("uid") long uid, @Part("image\"; filename=\"avatar.jpg\" ") RequestBody image);

        @Multipart
        @POST("/user/cover")
        Call<User> cover(@Query("uid") long uid, @Part("image\"; filename=\"cover.jpg\" ") RequestBody image);

        @GET("/user")
        Call<User> get(@Query("uid") long uid, @Query("uid2") long uid2);

        @GET("/user/list")
        Call<List<User>> gets(@Query("getsType") GetsType type,
                              @Query("uid") long uid,
                              @Query("keyword") String keyword,
                              @Query("pageIndex") int pageIndex,
                              @Query("pageSize") int pageSize);

        @GET("/user/team/list")
        Call<List<User>> getTeams(@Query("uid") long uid,
                                  @Query("groupName") String name);


        @POST("/user/team/focus")
        Call<Object> focusTeams(@Query("uid") long uid, @Query("uid2s") String uid2s);


        @POST("/user/focus")
        Call<Object> focus(@Query("uid") long uid, @Query("uid2") long uid2, @Query("focus") boolean focus);


    }

    private UserRestful() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        user = (User) FileUtils.getObject(Constants.KEY_USER);
        if (user != null) {
            user.init();
        }

    }


    //注册，登录，修改信息
    public interface OnUserRestfulGet {

        void onSuccess(User user);

        void onError(RestfulError error);

        void onFinish();

    }

    //添加关注，取消关注
    public interface OnUserRestfulDo {
        void onSuccess();

        void onError(RestfulError error);

        void onFinish();

    }

    //好友列表，粉丝列表
    public interface OnUserRestfulList {
        void onSuccess(List<User> users);

        void onError(RestfulError error);

        void onFinish();

    }


    //当前用户
    public User me() {
        return user;
    }

    public long meId() {
        return user != null ? user.id : 0;
    }

    public boolean isLogin() {
        return me() != null;
    }


    //退出
    public boolean logout() {
        Call<Object> call = userService.logout(meId());
        call.enqueue(new OnRestful<Object>() {
            @Override
            void onSuccess(Object object) {
            }

            @Override
            void onError(RestfulError error) {
            }

            @Override
            void onFinish() {
            }
        });
        user = null;
        boolean isLogout = FileUtils.deleteObject(Constants.KEY_USER);
        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_LOGOUT));
        return isLogout;
    }

    //注册
    public void register(String email, String password, @NonNull final OnUserRestfulGet onGet) {
        Call<User> call = userService.register(email, password, UmengRegistrar.getRegistrationId(Application.INSTANCE));
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                updateMe(user);
                onGet.onSuccess(user);
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


    //注册
    public void loginsso(String email, String password, String name, String avatar, User.GenderType gender, @NonNull final OnUserRestfulGet onGet) {
        Call<User> call = userService.loginsso(email, password, name, avatar,gender, UmengRegistrar.getRegistrationId(Application.INSTANCE));
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                updateMe(user);
                onGet.onSuccess(user);
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


    //登录
    public void login(String email, String password, @NonNull final OnUserRestfulGet onGet) {
        Call<User> call = userService.login(email, password, UmengRegistrar.getRegistrationId(Application.INSTANCE));
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                updateMe(user);
                onGet.onSuccess(user);
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


    //修改
    public void edit(User user, @NonNull final OnUserRestfulGet onGet) {

        Call<User> call = userService.edit(user);
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                updateMe(user);
                onGet.onSuccess(user);
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


    public void updateMe(User user) {
        UserRestful.this.user = user;
        FileUtils.setObject(Constants.KEY_USER, UserRestful.this.user);
    }


    //上传头像
    public void avatar(Bitmap image, @NonNull final OnUserRestfulGet onGet) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), baos.toByteArray());
        Call<User> call = userService.avatar(meId(), requestBody);
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                updateMe(user);
                onGet.onSuccess(user);
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


    //获取单个用户
    public void get(long uid, @NonNull final OnUserRestfulGet onGet) {
        Call<User> call = userService.get(meId(), uid);
        call.enqueue(new OnRestful<User>() {
            @Override
            void onSuccess(User user) {
                onGet.onSuccess(user);
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


    //获取用户列表
    public enum GetsType {

        NORMAL(0),
        FRIEND(1),
        FOCUS(2),
        FANS(3),
        DISCOVER(4),
        SEARCH(5);

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


    public void gets(final GetsType getsType, long uid, String keyword, int pageIndex, @NonNull final OnUserRestfulList onList) {
        Call<List<User>> call = userService.gets(getsType, uid, keyword, pageIndex, Constants.PAGE_SIZE);
        call.enqueue(new OnRestful<List<User>>() {
            @Override
            void onSuccess(List<User> users) {
                if (users != null) {
                    for (User user : users) {
                        if (getsType == GetsType.NORMAL || getsType == GetsType.SEARCH) {
                            user.mainType = User.UserMainType.NORMAL;
                        } else if (getsType == GetsType.DISCOVER) {
                            user.mainType = User.UserMainType.DISCOVER;
                        } else {
                            user.mainType = User.UserMainType.CONTACT;
                        }

                    }
                }

                onList.onSuccess(users);
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

    public void searchUsers(String keyword, int pageIndex, @NonNull final OnUserRestfulList onList) {

    }


    //获取球队
    public void getTeams(String name, @NonNull final OnUserRestfulList onList) {
        Call<List<User>> call = userService.getTeams(UserRestful.INSTANCE.meId(), name);
        call.enqueue(new OnRestful<List<User>>() {
            @Override
            void onSuccess(List<User> users) {
                onList.onSuccess(users);
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


    //关注
    public void focus(long uid, boolean focus, @NonNull final OnUserRestfulDo onDo) {
        Call<Object> call = userService.focus(meId(), uid, focus);
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

    //关注
    public void focusTeams(List<User> users, @NonNull final OnUserRestfulDo onDo) {
        List<Long> uids = new ArrayList<>();
        for (User user : users) {
            uids.add(user.id);
        }
        String teamUids = TextUtils.join(";", uids);
        Call<Object> call = userService.focusTeams(meId(), teamUids);
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
