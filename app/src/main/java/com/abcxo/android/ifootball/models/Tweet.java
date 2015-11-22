package com.abcxo.android.ifootball.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.controllers.fragments.main.TweetFragment;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.utils.NavUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet extends BaseObservable implements Parcelable {
    public long id;
    public long uid;


    public int commentCount;
    public int repeatCount;
    @Bindable
    public int starCount;
    @Bindable
    public boolean star;


    public String icon;
    public String name;
    public String title;
    public String source;
    public String summary;
    public String content;
    public String cover;
    public String url;
    public String lon;
    public String lat;
    public String images;
    public String time;

    public TweetMainType mainType = TweetMainType.NORMAL;
    public TweetDetailType detailType = TweetDetailType.TWEET;

    public Tweet originTweet;

    public transient BindingHandler handler = new BindingHandler();


    public Tweet() {
        super();
    }

    protected Tweet(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        commentCount = in.readInt();
        repeatCount = in.readInt();
        starCount = in.readInt();
        star = in.readByte() != 0;
        icon = in.readString();
        name = in.readString();
        title = in.readString();
        source = in.readString();
        summary = in.readString();
        content = in.readString();
        cover = in.readString();
        url = in.readString();
        lon = in.readString();
        lat = in.readString();
        images = in.readString();
        time = in.readString();
        originTweet = in.readParcelable(Tweet.class.getClassLoader());
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
        }
        return imageList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(uid);
        dest.writeInt(commentCount);
        dest.writeInt(repeatCount);
        dest.writeInt(starCount);
        dest.writeByte((byte) (star ? 1 : 0));
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(source);
        dest.writeString(summary);
        dest.writeString(content);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeString(images);
        dest.writeString(time);
        dest.writeParcelable(originTweet, flags);
    }


    public enum TweetMainType {

        NORMAL(0),
        TEAM(1),
        NEWS(2),
        SPECIAL(3);
        private int index;

        TweetMainType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetMainType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum TweetDetailType {

        TWEET(0),
        NEWS(1);
        private int index;

        TweetDetailType(int index) {
            this.index = index;
        }

        public static int size() {
            return TweetDetailType.values().length;
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
            if (detailType == Tweet.TweetDetailType.TWEET) {
                NavUtils.toTweetDetail(view.getContext(), Tweet.this);
            } else if (detailType == Tweet.TweetDetailType.NEWS) {
                NavUtils.toNewsDetail(view.getContext(), Tweet.this);
            }

        }

        public void onClickUser(View view) {
            NavUtils.toUserDetail(view.getContext(), uid);
        }


        public void onClickShare(View view){

        }

        public void onClickRepeat(View view) {
            NavUtils.toAddTweet(view.getContext(), Tweet.this);
        }

        public void onClickStar(final View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            star = !star;
            if (star) {
                starCount++;
            } else {
                starCount--;
            }
//            binding.setVariable(BR.tweet, Tweet.this);
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
        }
    }
}
