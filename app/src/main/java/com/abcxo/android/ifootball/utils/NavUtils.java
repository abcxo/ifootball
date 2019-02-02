package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.ChatDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.ContactActivity;
import com.abcxo.android.ifootball.controllers.activities.DataDetailActivity;
import com.abcxo.android.ifootball.controllers.activities.ImageActivity;
import com.abcxo.android.ifootball.controllers.activities.LiveActivity;
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

import cn.sharesdk.onekeyshare.OnekeyShare;

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

    public static void toLive(Context context) {
        Intent intent = new Intent(context, LiveActivity.class);
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

    public static void toShare(Context context, String title, String summary, String path, String cover) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(Constants.SITE);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(summary);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(path);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Constants.SITE);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//            oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(Constants.SITE);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Constants.SITE);
        oks.setImageUrl(cover);
// 启动分享GUI
        oks.show(context);
    }
}
