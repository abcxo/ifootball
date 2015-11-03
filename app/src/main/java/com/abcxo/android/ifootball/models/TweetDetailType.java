package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
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
