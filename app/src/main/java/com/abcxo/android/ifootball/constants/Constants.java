package com.abcxo.android.ifootball.constants;

import com.abcxo.android.ifootball.Application;

/**
 * Created by shadow on 15/11/3.
 */
public final class Constants {

    public static final int REQUEST_CAMERA = 1000;
    public static final int REQUEST_PHOTO = 1001;

    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_LOGOUT = "logout";


    public static final String HOST = "http://123.56.119.95:8080";
    public static final int PAGE_INDEX = 0;
    public static final int PAGE_SIZE = 30;


    public static String KEY_TWEET = "tweet";
    public static String KEY_USER = "user";
    public static String KEY_UID = "uid";
    public static String KEY_UID2 = "uid2";
    public static String KEY_TID = "tid";
    public static String KEY_MESSAGE = "message";

    public static String KEY_IMAGES = "images";
    public static String KEY_IMAGES_INDEX = "images_index";

    public static String DIR = Application.INSTANCE.getFilesDir().getAbsolutePath();

    public static String DIR_ADD_TWEET = DIR + "/addtweet/";

    public static int MAX_ADD_TWEET_IMAGE = 9;


}
