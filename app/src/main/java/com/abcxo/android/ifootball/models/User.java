package com.abcxo.android.ifootball.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class User implements Parcelable, Serializable {
    public long id;
    public String index;
    public String username;
    public String email;
    public String name;
    public String sign;
    public String password;
    public String avatar;
    public String cover;
    public String distance;
    public String time;
    public String lon;
    public String lat;
    public int focusCount;
    public int fansCount;
    public GenderType gender = GenderType.MALE;
    public UserType userType = UserType.NORMAL;
    public UserMainType mainType = UserMainType.NORMAL;

    public User() {
        super();
    }

    protected User(Parcel in) {
        id = in.readLong();
        index = in.readString();
        username = in.readString();
        email = in.readString();
        name = in.readString();
        sign = in.readString();
        password = in.readString();
        avatar = in.readString();
        cover = in.readString();
        distance = in.readString();
        time = in.readString();
        lon = in.readString();
        lat = in.readString();
        focusCount = in.readInt();
        fansCount = in.readInt();
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
        dest.writeLong(id);
        dest.writeString(index);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(sign);
        dest.writeString(password);
        dest.writeString(avatar);
        dest.writeString(cover);
        dest.writeString(distance);
        dest.writeString(time);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeInt(focusCount);
        dest.writeInt(fansCount);
    }


    public enum GenderType {

        MALE(0),
        FEMALE(1);
        private int index;

        GenderType(int index) {
            this.index = index;
        }

        public static int size() {
            return GenderType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    public enum UserType {

        NORMAL(0),
        TEAM(1),
        VIP(2),
        SUPER(3);
        private int index;

        UserType(int index) {
            this.index = index;
        }

        public static int size() {
            return UserType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum UserMainType {

        NORMAL(0),
        CONTACT(1),
        DISCOVER(2),
        SPECIAL(3);
        private int index;

        UserMainType(int index) {
            this.index = index;
        }

        public static int size() {
            return UserMainType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


}
