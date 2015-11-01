package com.abcxo.android.ifootball.restfuls;

import com.abcxo.android.ifootball.models.User;

/**
 * Created by shadow on 15/11/1.
 */
public class UserRestful {
    private static UserRestful ourInstance = new UserRestful();

    public static UserRestful getInstance() {
        return ourInstance;
    }

    private UserRestful() {
    }

    public User getLoginUser(){
        User user = new User();
        user.id ="1";
        user.username = "abcxo";
        user.nickname = "咸蛋超人";
        user.signature = "西甲一个人过21个人的那个门将就是我...";
        user.avatar = "http://img1.imgtn.bdimg.com/it/u=1252800744,57876037&fm=23&gp=0.jpg";
        user.cover= "http://img3.imgtn.bdimg.com/it/u=2254914422,1826964007&fm=21&gp=0.jpg";
        user.gender = 1;
        return user;
    }
}
