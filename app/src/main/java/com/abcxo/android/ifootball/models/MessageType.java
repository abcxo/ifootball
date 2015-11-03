package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
public enum MessageType {

    MESSAGE(0),
    FOCUS(1),
    REPLY(2),
    PROMPT(3),
    REPLY(4),
    CHAT(5);
    private int index;

    MessageType(int index) {
        this.index = index;
    }

    public static int size() {
        return MessageType.values().length;
    }

    public int getIndex() {
        return index;
    }
}
