package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.abcxo.android.ifootball.utils.NavUtils;

import java.util.ArrayList;
import java.util.List;

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
    }
}
