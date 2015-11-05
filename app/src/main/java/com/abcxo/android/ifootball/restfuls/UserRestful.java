package com.abcxo.android.ifootball.restfuls;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.RestfulConstants;
import com.abcxo.android.ifootball.models.GenderType;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.models.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class UserRestful {
    public static UserRestful INSTANCE = new UserRestful();


    public String uid = "";


    private UserRestful() {
    }


    /**
     * 测试
     */

    private void post(Runnable runnable) {
        new Handler().postDelayed(runnable, 2000);
    }


    private User testUser() {
        User user = new User();
        user.id = "1";
        user.username = "abcxo";
        user.name = "咸蛋超人";
        user.sign = "西甲一个人过21个人的那个门将就是我...";
        user.pwd = "111";
        user.avatar = "http://img1.imgtn.bdimg.com/it/u=1252800744,57876037&fm=23&gp=0.jpg";
        user.cover = "http://img3.imgtn.bdimg.com/it/u=2254914422,1826964007&fm=21&gp=0.jpg";
        user.distance = "300m";
        user.time = "7分钟前";
        user.lon = "0";
        user.lat = "0";
        user.gender = GenderType.MALE;
        user.type = UserType.NORMAL;
        return user;
    }


    private List<User> testUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < RestfulConstants.PAGE_SIZE; i++) {
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
        return testUser();
    }


    //注册
    public void register(User user, @NonNull final OnUserRestfulGet onGet) {
        post(new Runnable() {
            @Override
            public void run() {
                onGet.onSuccess(testUser());
                onGet.onFinish();

            }
        });
    }

    //登录
    public void login(String name, String pwd, @NonNull final OnUserRestfulGet onGet) {
        post(new Runnable() {
            @Override
            public void run() {
                onGet.onSuccess(testUser());
                onGet.onFinish();

            }
        });
    }

    //修改
    public void edit(User user, @NonNull final OnUserRestfulGet onGet) {
        post(new Runnable() {
            @Override
            public void run() {
                onGet.onSuccess(testUser());
                onGet.onFinish();

            }
        });
    }

    //获取单个用户
    public void getUser(String uid, @NonNull final OnUserRestfulGet onGet) {
        post(new Runnable() {
            @Override
            public void run() {
                onGet.onSuccess(testUser());
                onGet.onFinish();

            }
        });
    }


    //获取用户列表
    public enum GetsType {
        DISCOVER,
        FRIEND,
        FOCUS,
        FANS,
    }

    public void getUsers(GetsType getsType, int pageIndex, @NonNull final OnUserRestfulList onList) {
        getUsers(uid, getsType, pageIndex, onList);
    }

    public void getUsers(String uid, GetsType getsType, int pageIndex, @NonNull final OnUserRestfulList onList) {
        post(new Runnable() {
            @Override
            public void run() {
                onList.onSuccess(testUsers());
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
    public void focus(String uid, @NonNull final OnUserRestfulDo onDo) {
        post(new Runnable() {
            @Override
            public void run() {
                onDo.onSuccess();
                onDo.onFinish();
            }
        });
    }

    //取消关注
    public void cancelFocus(String uid, @NonNull final OnUserRestfulDo onDo) {
        post(new Runnable() {
            @Override
            public void run() {
                onDo.onSuccess();
                onDo.onFinish();
            }
        });
    }

}
