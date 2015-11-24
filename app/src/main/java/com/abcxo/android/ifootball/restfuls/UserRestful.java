package com.abcxo.android.ifootball.restfuls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.models.User.GenderType;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

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

    /**
     * 测试
     */

    private void post(Runnable runnable) {
        new Handler().postDelayed(runnable, 2000);
    }


    private User testUser() {
        User user = new User();
        user.id = 1L;
        user.username = "abcxo";
        user.email = "abcxo@qq.com";
        user.name = "咸蛋超人";
        user.sign = "西甲一个人过21个人的那个门将就是我...";
        user.password = "111";
        user.avatar = "http://img1.imgtn.bdimg.com/it/u=1252800744,57876037&fm=23&gp=0.jpg";
        user.cover = "http://img3.imgtn.bdimg.com/it/u=2254914422,1826964007&fm=21&gp=0.jpg";
        user.distance = "300m";
        user.time = "7分钟前";
        user.gender = GenderType.MALE;
        user.userType = User.UserType.NORMAL;
        return user;
    }


    private List<User> testUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < Constants.PAGE_SIZE; i++) {
            users.add(testUser());
        }
        return users;
    }

    private List<List<User>> testTeamUsers() {
        List<List<User>> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            users.add(testUsers());
        }
        return users;
    }

    /**
     * 测试
     */


    public static UserRestful INSTANCE = new UserRestful();


    private User user;


    private UserService userService;

    public interface UserService {
        @GET("/user/login")
        Call<User> login(@Query("email") String email, @Query("password") String password);

        @POST("/user/register")
        Call<User> register(@Query("email") String email, @Query("password") String password);


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
        Call<List<User>> gets(@Query("uid") long uid,
                              @Query("getsType") GetsType type,
                              @Query("pageIndex") int pageIndex,
                              @Query("pageSize") int pageSize);

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


    //获取球队列表
    public interface OnUserRestfulTeams {
        void onSuccess(List<List<User>> users);

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
        user = null;
        boolean isLogout = FileUtils.deleteObject(Constants.KEY_USER);
        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_LOGOUT));
        return isLogout;
    }

    //注册
    public void register(String email, String password, @NonNull final OnUserRestfulGet onGet) {
        Call<User> call = userService.register(email, password);
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
        Call<User> call = userService.login(email, password);
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

        FRIEND(0),
        FOCUS(1),
        FANS(2),
        DISCOVER(3);

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


    public void gets(long uid, GetsType getsType, int pageIndex, @NonNull final OnUserRestfulList onList) {
        Call<List<User>> call = userService.gets(uid, getsType, pageIndex, Constants.PAGE_SIZE);
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

    public void searchUsers(String keyword, int pageIndex, @NonNull final OnUserRestfulList onList) {
        post(new Runnable() {
            @Override
            public void run() {
                onList.onSuccess(testUsers());
                onList.onFinish();

            }
        });
    }


    //获取球队
    public void getTeams(@NonNull final OnUserRestfulTeams onTeams) {
        post(new Runnable() {
            @Override
            public void run() {
                onTeams.onSuccess(testTeamUsers());
                onTeams.onFinish();

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


}
