package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.ChatDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.ContactActivity;
import com.abcxo.android.ifootball.controllers.activities.DataDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.ImageActivity;
import com.abcxo.android.ifootball.controllers.activities.MessageActivity;
import com.abcxo.android.ifootball.controllers.activities.SearchActivity;
import com.abcxo.android.ifootball.controllers.activities.SignActivity;
import com.abcxo.android.ifootball.controllers.activities.TweetDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.UserDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.WebActivity;
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

    public static void toDataDetail(Context context) {
        Intent intent = new Intent(context, DataDetailActivity.class);
        context.startActivity(intent);
    }

    public static void toWeb(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_URL, url);
        bundle.putString(Constants.KEY_TITLE, title);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toWeb(Context context, String url) {
        toWeb(context, url, null);
    }

    public static void toBrowser(Context context, String url) {
        Uri uri = Uri.parse(url.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    public static void toSearch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void toMessage(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }


    public static void toLocation(Context context, double lat, double lon, String location) {
        Uri uri = Uri.parse(String.format("geo:%f,%f?q=%s", lat, lon, location));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void toEmail(Context context) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:iamthefootball@qq.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "关于足球狗建议反馈");
        data.putExtra(Intent.EXTRA_TEXT, "说说您的看法！");
        context.startActivity(data);
    }

    public static void toContact(Fragment context, int requestCode) {
        Intent intent = new Intent(context.getActivity(), ContactActivity.class);
        context.startActivityForResult(intent, requestCode);
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


    public static void toUserDetail(Context context, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_NAME, name);
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

    public static void toChatDetail(Context context, long uid, long uid2) {
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


    public static void toComment(Context context, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        bundle.putBoolean(Constants.KEY_IS_COMMENT, true);
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
        toImage(context, images, 0, null);
    }

    public static void toImage(Context context, ArrayList<Image> images, int index) {
        toImage(context, images, index, null);
    }

    public static void toImage(Context context, ArrayList<Image> images, int index, Tweet tweet) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_IMAGES, images);
        bundle.putInt(Constants.KEY_IMAGES_INDEX, index);
        bundle.putParcelable(Constants.KEY_TWEET, tweet);
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
