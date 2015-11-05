package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
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
