package com.abcxo.android.ifootball.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.TweetDetailActivity;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.IconFontView;
import com.abcxo.android.ifootball.views.ReverseInterpolator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet extends BaseObservable implements Parcelable, Serializable {
    public long id;
    //用户
    public long uid;
    public String icon;
    public String name;

    //操作
    public int commentCount;
    public int repeatCount;
    @Bindable
    public int starCount;


    //推文内容
    public String title;
    public String source;
    public String summary;
    public String content;


    public String images;
    public String imageTitles;
    public String time;

    public double lon;
    public double lat;
    @Bindable
    public String location;

    public Tweet originTweet;


    public List<Message> messages;

    @Bindable
    public boolean star;
    public TweetType tweetType = TweetType.NORMAL;
    public TweetContentType tweetContentType = TweetContentType.NORMAL;


    private transient BindingHandler handler = new BindingHandler();

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }

    public Tweet() {
        super();
    }

    protected Tweet(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        icon = in.readString();
        name = in.readString();
        commentCount = in.readInt();
        repeatCount = in.readInt();
        starCount = in.readInt();
        title = in.readString();
        source = in.readString();
        summary = in.readString();
        content = in.readString();
        images = in.readString();
        imageTitles = in.readString();
        time = in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
        location = in.readString();
        originTweet = in.readParcelable(Tweet.class.getClassLoader());
        messages = in.readArrayList(Message.class.getClassLoader());
        star = in.readByte() != 0;
        tweetType = TweetType.valueOf(in.readString());
        tweetContentType = TweetContentType.valueOf(in.readString());
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public List<Image> imageList() {
        List<Image> imageList = new ArrayList<>();
        if (!TextUtils.isEmpty(images)) {
            List<String> list = Arrays.asList(images.split(";"));
            for (String url : list) {
                Image image = new Image();
                image.url = url;

                imageList.add(image);
            }
            if (!TextUtils.isEmpty(imageTitles)) {
                List<String> listTitle = Arrays.asList(imageTitles.split(";"));
                for (int i = 0; i < imageList.size(); i++) {
                    Image image = imageList.get(i);
                    image.title = "_".equals(listTitle.get(i)) ? null : listTitle.get(i);
                    image.imageType = Image.ImageType.TWEET;
                }
            }

        }
        return imageList;
    }

    public SpannableString getSummary() {
        return ViewUtils.getPromptString(summary);
    }


    public Message message() {
        return messages != null && messages.size() > 0 ? messages.get(0) : null;
    }

    public Message message1() {
        return messages != null && messages.size() > 1 ? messages.get(1) : null;
    }

    public Message message2() {
        return messages != null && messages.size() > 2 ? messages.get(2) : null;
    }

    public String cover() {
        String cover = null;
        if (!TextUtils.isEmpty(images)) {
            List<String> list = Arrays.asList(images.split(";"));
            cover = list.get(0);
        }
        return cover;
    }


    public boolean hasImage(String url) {
        List<Image> imageList = imageList();
        for (Image image : imageList) {
            if (image.url.equals(url)) {
                return true;
            }
        }
        return false;
    }

    public int indexOfImage(String url) {
        List<Image> imageList = imageList();
        int i = 0;
        for (Image image : imageList) {
            if (image.url.equals(url)) {
                return i;
            }
            i++;
        }
        return i;
    }


    public TweetMainType getMainType() {
        if (tweetType == TweetType.NEWS && !TextUtils.isEmpty(imageTitles) && imageTitles.split(";").length >= 3) {
            return TweetMainType.IMAGE;
        }
        return TweetMainType.valueOf(tweetType.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(uid);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeInt(commentCount);
        dest.writeInt(repeatCount);
        dest.writeInt(starCount);
        dest.writeString(title);
        dest.writeString(source);
        dest.writeString(summary);
        dest.writeString(content);
        dest.writeString(images);
        dest.writeString(imageTitles);
        dest.writeString(time);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeString(location);
        dest.writeParcelable(originTweet, flags);
        dest.writeList(messages);
        dest.writeByte((byte) (star ? 1 : 0));
        dest.writeString(tweetType.name());
        dest.writeString(tweetContentType.name());
    }


    public enum TweetType {

        NORMAL(0),
        TEAM(1),
        NEWS(2),
        PUBLIC(3),
        SPECIAL(4);
        private int index;

        TweetType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    public enum TweetContentType {

        NORMAL(0),
        IMAGES(1),
        VIDEO(2);
        private int index;

        TweetContentType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum TweetMainType {

        NORMAL(0),
        TEAM(1),
        NEWS(2),
        IMAGE(3),
        PUBLIC(3),
        SPECIAL(4);
        private int index;

        TweetMainType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public class BindingHandler {
        public void onClickImage(View view) {
            NavUtils.toImage(view.getContext(), (ArrayList<Image>) imageList());
        }

        public void onClickTweet(View view) {
            if (getMainType() != TweetMainType.IMAGE) {
                NavUtils.toTweetDetail(view.getContext(), Tweet.this);
            } else {
                NavUtils.toImage(view.getContext(), (ArrayList<Image>) imageList(), 0, Tweet.this);
            }

        }

        public void onClickComment(View view) {
            NavUtils.toComment(view.getContext(), Tweet.this);
        }

        public void onClickUser(View view) {
            NavUtils.toUserDetail(view.getContext(), uid);
        }


        public void onClickRepeat(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                NavUtils.toAddTweet(view.getContext(), Tweet.this);
            } else {
                NavUtils.toSign(view.getContext());
            }
        }

        public void onClickLocation(View view) {
            NavUtils.toLocation(view.getContext(), lat, lon, location);
        }

        public void onClickStar(final View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                star = !star;
                if (star) {
                    starCount++;
                } else {
                    starCount--;
                }
                notifyPropertyChanged(BR.star);
                notifyPropertyChanged(BR.starCount);
                TweetRestful.INSTANCE.star(id, star, new TweetRestful.OnTweetRestfulDo() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(RestfulError error) {
                    }

                    @Override
                    public void onFinish() {

                    }
                });
            } else {
                NavUtils.toSign(view.getContext());
            }
        }


        public void onClickShare(final View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                ViewUtils.loading(view.getContext());
                final String cover = cover();
                Picasso.with(Application.INSTANCE).load(cover).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String path = FileUtils.saveImage(bitmap, Constants.DIR_TWEET_SHARE, Utils.md5(cover));
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
                        oks.show(view.getContext());
                        ViewUtils.dismiss();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        ViewUtils.dismiss();
                        ViewUtils.toast(R.string.error_share);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            } else {
                NavUtils.toSign(view.getContext());
            }
        }
    }
}
