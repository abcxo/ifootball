package com.abcxo.android.ifootball.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shadow on 15/10/31.
 */
public class Content {
    public String id = "";
    public String title = "";
    public String summary = "";
    public String text = "";
    public String cover = "";
    public String url = "";
    public String lon = "";
    public String lat = "";
    public List<String> images = new ArrayList<>();
    public Map<String, String> extras = new HashMap<>();
}
