package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by shadow on 15/11/17.
 */
public class Data implements Parcelable, Serializable {
    public String url;
    public String name;
    public String category;
    private transient BindingHandler handler = new BindingHandler();

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }

    public Data() {
        super();
    }

    protected Data(Parcel in) {
        url = in.readString();
        name = in.readString();
        category = in.readString();

    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(category);
    }


    public class BindingHandler {
    }
}
