package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.ChatDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.ImageActivity;
import com.abcxo.android.ifootball.controllers.activities.SignActivity;
import com.abcxo.android.ifootball.controllers.activities.TweetDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.UserDetailActivity;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;

import java.util.ArrayList;

/**
 * Created by shadow on 15/11/14.
 */
public class NavUtils {

    public static void toSign(View view) {
        toSign(view.getContext());
    }

    public static void toSign(Context context) {
        Intent intent = new Intent(context, SignActivity.class);
        context.startActivity(intent);
    }


    public static void toAddTweet(Context context, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        Intent intent = new Intent(context, AddTweetActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toUserDetail(Context context, User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_USER, user);
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toUserDetail(Context context, long uid) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, uid);
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toChatDetail(Context context,long uid,long uid2) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, uid);
        bundle.putLong(Constants.KEY_UID2, uid2);
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toTweetDetail(Context context, long tid) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_TID, tid);
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toTweetDetail(Context context, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void toImage(Context context, ArrayList<Image> images) {
        toImage(context, images, 0);
    }

    public static void toImage(Context context, ArrayList<Image> images, int index) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_IMAGES, images);
        bundle.putInt(Constants.KEY_IMAGES_INDEX, index);
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
