package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
public enum TweetType {

    USER(0),
    TEAM(1),
    NEWS(2);
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
