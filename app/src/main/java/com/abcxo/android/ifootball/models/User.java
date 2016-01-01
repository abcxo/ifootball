package com.abcxo.android.ifootball.models;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;

import java.io.Serializable;

/**
 * Created by SHARON on 15/10/29.
 */
public class User extends BaseObservable implements Parcelable, Serializable {
    public long id;
    public String letter;
    public String groupName;
    public String email;
    public String name;
    public String sign;
    public String password;
    public String avatar;
    public String cover;
    public String position;
    public String teamIcon;

    public double lon;
    public double lat;
    public int focusCount;
    public int fansCount;


    @Bindable
    public boolean focus;
    public String distance;
    public String deviceToken;

    public GenderType gender = GenderType.MALE;
    public UserType userType = UserType.NORMAL;
    public UserMainType mainType = UserMainType.NORMAL;


    private transient BindingHandler handler = new BindingHandler();

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }


    public User() {
        super();
    }

    protected User(Parcel in) {
        id = in.readLong();
        letter = in.readString();
        groupName = in.readString();
        email = in.readString();
        name = in.readString();
        sign = in.readString();
        password = in.readString();
        avatar = in.readString();
        cover = in.readString();
        position = in.readString();
        teamIcon= in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
        focusCount = in.readInt();
        fansCount = in.readInt();
        focus = in.readByte() != 0;
        distance = in.readString();
        deviceToken = in.readString();
        gender = GenderType.valueOf(in.readString());
        userType = UserType.valueOf(in.readString());
        mainType = UserMainType.valueOf(in.readString());
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


    public boolean isMe() {
        return id == UserRestful.INSTANCE.meId();
    }



    public boolean canDo() {
        return isMe() == false && (userType != UserType.SPECIAL);
    }

    public boolean canChat() {
        return isMe() == false && userType == UserType.NORMAL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(letter);
        dest.writeString(groupName);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(sign);
        dest.writeString(password);
        dest.writeString(avatar);
        dest.writeString(cover);
        dest.writeString(position);
        dest.writeString(teamIcon);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeInt(focusCount);
        dest.writeInt(fansCount);
        dest.writeByte((byte) (focus ? 1 : 0));
        dest.writeString(distance);
        dest.writeString(deviceToken);
        dest.writeString(gender.name());
        dest.writeString(userType.name());
        dest.writeString(mainType.name());
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
        NEWS(2),
        PUBLIC(3),
        SPECIAL(4);
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
        DISCOVER(2);
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

    public class BindingHandler {
        public void onClickAvatar(View view) {
            Image image = new Image();
            image.url = avatar;
            image.getHandler().onClickImage(view);
        }

        public void onClickUser(View view) {
            NavUtils.toUserDetail(view.getContext(), User.this);
        }

        public void onClickChat(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                NavUtils.toChatDetail(view.getContext(), UserRestful.INSTANCE.meId(), id);
            } else {
                NavUtils.toSign(view.getContext());
            }

        }

        public void onClickFocus(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                focus = !focus;
                notifyPropertyChanged(BR.focus);
                UserRestful.INSTANCE.focus(id, focus, new UserRestful.OnUserRestfulDo() {
                    @Override
                    public void onSuccess() {
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_LOGIN));
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


    }

}
