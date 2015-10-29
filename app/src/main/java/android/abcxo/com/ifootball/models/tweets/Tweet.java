package android.abcxo.com.ifootball.models.tweets;

import android.abcxo.com.ifootball.models.raws.Raw;
import android.abcxo.com.ifootball.models.raws.Location;
import android.abcxo.com.ifootball.models.users.User;

import java.util.List;

/**
 * Created by SHARON on 15/10/29.
 */
public class Tweet {
    public String id;
    public User user;
    public String content;
    public Raw extraContent;
    public Location location;
    public List<String> images;
    public int goodCount;
    public int forwardCount;
    public int replyCount;
    public String time;
}
