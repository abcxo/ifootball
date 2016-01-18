package com.abcxo.android.ifootball.models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by shadow on 15/11/17.
 */
public class Image implements Parcelable,Serializable {
    public String url;
    public String title;
    public ImageType imageType = ImageType.SAVE_SHARE;
    private transient BindingHandler handler = new BindingHandler();

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }

    public Image() {
        super();
    }

    protected Image(Parcel in) {
        url = in.readString();
        title = in.readString();
        imageType = ImageType.valueOf(in.readString());

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
        dest.writeString(title);
        dest.writeString(imageType.name());
    }


    public enum ImageType {

        SAVE_SHARE(0),
        DELETE(1),
        TWEET(2);

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

        public void onClickSave(final View view) {
            Picasso.with(Application.INSTANCE).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(), bitmap, null, null);
                    view.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(path)));
                    ViewUtils.toast(R.string.success_image_save);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    ViewUtils.dismiss();
                    ViewUtils.toast(R.string.error_image_save);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });

        }

        public void onClickShare(final View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                ViewUtils.loading(view.getContext());
                Picasso.with(Application.INSTANCE).load(url).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Application.packageName = Constants.PACKAGE_NAME;
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
