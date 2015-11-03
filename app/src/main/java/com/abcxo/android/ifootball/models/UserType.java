package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
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
