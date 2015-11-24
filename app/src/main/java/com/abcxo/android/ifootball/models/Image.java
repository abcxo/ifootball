package com.abcxo.android.ifootball.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by shadow on 15/11/17.
 */
public class Image implements Parcelable {
    public String url;
    public ImageType imageType = ImageType.SAVE_SHARE;
    public transient BindingHandler handler = new BindingHandler();

    public Image() {
        super();
    }

    protected Image(Parcel in) {
        url = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }


    public enum ImageType {

        SAVE_SHARE(0),
        DELETE(1);

        private int index;

        ImageType(int index) {
            this.index = index;
        }

        public static int size() {
            return ImageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public class BindingHandler {
        public void onClickImage(View view) {
            List<Image> images = new ArrayList<>();
            images.add(Image.this);
            NavUtils.toImage(view.getContext(), (ArrayList<Image>) images);
        }

        public void onClickSave(View view) {
        }

        public void onClickShare(final View view) {
            ViewUtils.loading(view.getContext());
            Picasso.with(Application.INSTANCE).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    String path = FileUtils.saveImage(bitmap, Constants.DIR_TWEET_SHARE, Utils.md5(url));
                    OnekeyShare oks = new OnekeyShare();
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();
                    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//                    oks.setTitle(title);
                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                    oks.setTitleUrl(Constants.SITE);
                    // text是分享文本，所有平台都需要这个字段
//                    oks.setText(content);
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
//                    oks.setImageUrl(cover);
// 启动分享GUI
                    oks.show(view.getContext());
                    ViewUtils.dismiss();
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    ViewUtils.dismiss();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
    }
}
