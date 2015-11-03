package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
public enum GenderType {

    UNKNOWN(0),
    MALE(1),
    FEMALE(2);
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
