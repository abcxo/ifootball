package com.abcxo.android.ifootball.models;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
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
    @Bindable
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

    @Bindable
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


    public SpannableString getTitleSummary() {
        if (!TextUtils.isEmpty(name)) {
            SpannableString mTitle = new SpannableString(name + " " + summary);
            StyleSpan span = new StyleSpan(Typeface.BOLD);
            mTitle.setSpan(span, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return mTitle;
        } else {
            return ViewUtils.getPromptString(summary);
        }
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
        if (originTweet != null) {
            cover = originTweet.cover();
        } else {
            if (!TextUtils.isEmpty(images)) {
                List<String> list = Arrays.asList(images.split(";"));
                cover = list.get(0);
            }
        }

        return cover;
    }


    public String getTitle() {
        return title;
    }


    public String getCoverTitle() {
        try {
            return !TextUtils.isEmpty(title) ? title : originTweet.title;
        } catch (Exception e) {

        }
        return null;
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
        PRO(1),
        TEAM(2),
        NEWS(3),
        PUBLIC(4),
        SPECIAL(5);
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
        PRO(1),
        TEAM(2),
        NEWS(3),
        IMAGE(4),
        PUBLIC(5),
        SPECIAL(6);
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
                NavUtils.toAddTweet(view.getContext(), originTweet != null ? originTweet : Tweet.this);
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
                final Context context = view.getContext();
                final String cover = cover();
                final String co = !TextUtils.isEmpty(cover) ? cover : cover;
                final String ti = !TextUtils.isEmpty(title) ? title : context.getString(R.string.app_name);
                final String sum = !TextUtils.isEmpty(summary) ? summary : context.getString(R.string.app_share);
                if (!TextUtils.isEmpty(cover)) {
                    Picasso.with(Application.INSTANCE).load(cover).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            String path = FileUtils.saveImage(bitmap, Constants.DIR_TWEET_SHARE, Utils.md5(cover));
                            NavUtils.toShare(context, ti, sum, path, co);
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
                    String path = Constants.DIR_TWEET_SHARE + "ic_launcher.png";
                    FileUtils.copyFilesFromRaw(view.getContext(), R.raw.ic_launcher, path);
                    NavUtils.toShare(context, ti, sum, path, co);
                    ViewUtils.dismiss();
                }

            } else {
                NavUtils.toSign(view.getContext());
            }
        }
    }
}
