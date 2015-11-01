package com.abcxo.android.ifootball.models;

import java.util.List;
import java.util.Map;

/**
 * Created by shadow on 15/10/31.
 */
public class Content {
    //公用
    public String title;

    //TEXT
    public String summary;
    public String text;


    //IMAGE
    public List<String> images;

    //VIDEO
    public String cover;
    public String url;

    //LOCATION
    public double lon;
    public double lat;

    public Map<String, String> extras;

}
