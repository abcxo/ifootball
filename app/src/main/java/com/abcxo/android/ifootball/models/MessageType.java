package com.abcxo.android.ifootball.models;

/**
 * Created by shadow on 15/11/3.
 */
public enum MessageType {

    NORMAL(0),
    FOCUS(1),
    COMMENT(2),
    PROMPT(3),
    STAR(4),
    CHAT(5),
    SPECIAL(6);

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
