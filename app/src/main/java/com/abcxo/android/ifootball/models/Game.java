package com.abcxo.android.ifootball.models;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shadow on 15/11/17.
 */
public class Game implements Parcelable, Serializable {
    public long id;
    public String title;
    public String content;
    public String section;
    public boolean focus;
    public long uid;
    public String icon;
    public String name;
    public String score;
    public long uid2;
    public String icon2;
    public String name2;
    public String score2;
    public List<Live> lives;
    public StateType stateType = StateType.PREPARE;
    public String time;
    public long date;


    public static class Live implements Parcelable, Serializable {
        public String title;
        public String url;

        protected Live(Parcel in) {
            title = in.readString();
            url = in.readString();
        }

        public static final Creator<Live> CREATOR = new Creator<Live>() {
            @Override
            public Live createFromParcel(Parcel in) {
                return new Live(in);
            }

            @Override
            public Live[] newArray(int size) {
                return new Live[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(url);
        }
    }


    private transient BindingHandler handler = new BindingHandler();

    protected Game(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        section = in.readString();
        focus = in.readByte() != 0;
        uid = in.readLong();
        icon = in.readString();
        name = in.readString();
        score = in.readString();
        uid2 = in.readLong();
        icon2 = in.readString();
        name2 = in.readString();
        score2 = in.readString();
        time = in.readString();
        date = in.readLong();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public BindingHandler getHandler() {
        if (handler == null) {
            handler = new BindingHandler();
        }
        return handler;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(section);
        dest.writeByte((byte) (focus ? 1 : 0));
        dest.writeLong(uid);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(score);
        dest.writeLong(uid2);
        dest.writeString(icon2);
        dest.writeString(name2);
        dest.writeString(score2);
        dest.writeString(time);
        dest.writeLong(date);
    }


    public enum StateType {
        PREPARE(0),
        ING(1),
        END(2);
        private int index;

        StateType(int index) {
            this.index = index;
        }

        public static int size() {
            return StateType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public class BindingHandler {

        public void onClickGame(final View view) {
            if (lives == null || lives.size() == 0) {
                ViewUtils.toast(R.string.error_game_none);
            } else {
                String[] titles = new String[lives.size()];
                for (int i = 0; i < titles.length; i++) {
                    titles[i] = lives.get(i).title;
                }
                new AlertDialog.Builder(view.getContext())
                        .setItems(titles, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Live live = lives.get(which);
                                if (!TextUtils.isEmpty(live.url)) {
                                    NavUtils.toWeb(view.getContext(), live.url, live.title);
                                } else {
                                    ViewUtils.toast(R.string.error_game_url);
                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        }
    }
}
