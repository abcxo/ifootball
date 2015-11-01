package com.abcxo.android.ifootball.restfuls;

/**
 * Created by shadow on 15/11/1.
 */
public class MessageRestful {
    private static MessageRestful ourInstance = new MessageRestful();

    public static MessageRestful getInstance() {
        return ourInstance;
    }

    private MessageRestful() {
    }
}
