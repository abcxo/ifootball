package com.abcxo.android.ifootball.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHARON on 15/10/29.
 */
public class User {
    public String id = "";
    public String username = "";
    public String name = "";
    public String sign = "";
    public String pwd = "";
    public String avatar = "";
    public String cover = "";
    public String distance = "";
    public String time = "";
    public String lon = "";
    public String lat = "";
    public GenderType gender = GenderType.UNKNOWN;
    public UserType type = UserType.NORMAL;
    public UserMainType mainType = UserMainType.NORMAL;
    public Map<String, String> extras = new HashMap<>();
}
