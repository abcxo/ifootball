package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
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
