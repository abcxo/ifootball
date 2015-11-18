package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.NewsDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.SignActivity;
import com.abcxo.android.ifootball.controllers.activities.TweetDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.UserDetailActivity;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;

/**
 * Created by shadow on 15/11/14.
 */
public class NavUtils {
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

    public static void toTweetDetail(Context context, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toNewsDetail(Context context, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
}
