package com.abcxo.android.ifootball.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class Message{
    public String id = "";
    public String uid = "";
    public String icon = "";
    public String name = "";
    public String time = "";
    public Content content = new Content();
    public MessageType type = MessageType.MESSAGE;
    public Map<String, String> extras = new HashMap<>();

}
