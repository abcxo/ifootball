package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class User implements Parcelable {
    public String id = "";
    public String username = "";
    public String name = "";
    public String sign = "";
    public String pwd = "";
    public String avatar = "";
    public String cover = "";
    public String distance = "";
    public String time = "";
    public String lon = "";
    public String lat = "";
    public GenderType gender = GenderType.MALE;
    public UserType type = UserType.NORMAL;
    public UserMainType mainType = UserMainType.NORMAL;
    public Map<String, String> extras = new HashMap<>();

    public User(){
        super();
    }


    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        sign = in.readString();
        pwd = in.readString();
        avatar = in.readString();
        cover = in.readString();
        distance = in.readString();
        time = in.readString();
        lon = in.readString();
        lat = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(sign);
        dest.writeString(pwd);
        dest.writeString(avatar);
        dest.writeString(cover);
        dest.writeString(distance);
        dest.writeString(time);
        dest.writeString(lon);
        dest.writeString(lat);
    }
}
