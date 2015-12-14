package com.abcxo.android.ifootball.constants;

import android.os.Environment;

/**
 * Created by shadow on 15/11/3.
 */
public final class Constants {

    public static final int REQUEST_CAMERA = 1000;
    public static final int REQUEST_PHOTO = 1001;

    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_LOGOUT = "logout";

    public static final String ACTION_REFRESH_HOME = "refresh_home";
    public static final String ACTION_REFRESH_TEAM = "refresh_team";
    public static final String ACTION_REFRESH_CONTACT = "refresh_contact";
    public static final String ACTION_MESSAGE = "message";
    public static final String ACTION_MESSAGE_CLICK = "message_click";


    public static final String HOST = "http://www.iamthefootball.com:8080";
    //    public static final String HOST = "http://localhost:8080";
    public static final String SITE = "http://a.app.qq.com/o/simple.jsp?pkgname=com.abcxo.android.ifootball";

    public static final int PAGE_INDEX = 0;
    public static final int PAGE_SIZE = 30;


    public static String KEY_TWEET = "tweet";
    public static String KEY_USER = "user";
    public static String KEY_UID = "uid";
    public static String KEY_UID2 = "uid2";
    public static String KEY_TID = "tid";
    public static String KEY_NAME = "name";
    public static String KEY_MESSAGE = "message";

    public static String KEY_IMAGES = "images";
    public static String KEY_IMAGES_INDEX = "images_index";

    public static String DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ifootball";

    public static String DIR_TWEET_ADD = DIR + "/tweetadd/";
    public static String DIR_TWEET_SHARE = DIR + "/tweetshare/";
    public static String DIR_TWEET_CACHE = DIR + "/tweetcache/";

    public static int MAX_ADD_TWEET_IMAGE = 9;

    public static int MAX_LEFT_MORE = 5;

    public static String PREFERENCE = "ifootball";
    public static String PREFERENCE_FIRST = "first";

}
