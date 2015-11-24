package com.abcxo.android.ifootball.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Patterns;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.constants.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;

/**
 * Created by shadow on 15/11/14.
 */
public class Utils {

    public static void registerBroadcastReceiver(BroadcastReceiver receiver, String action) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(action));
    }

    public static void unregisterBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }


    public static boolean hasEmpty(String str) {
        return str.isEmpty() || str.contains(" ");
    }

    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6,10}");
        return pattern.matcher(password).matches();
    }


    public static boolean isName(String name) {
        Pattern pattern = Pattern.compile(".{4,20}");
        return pattern.matcher(name).matches();
    }


    public static boolean isSign(String sign) {
        Pattern pattern = Pattern.compile(".{0,40}");
        return pattern.matcher(sign).matches();
    }

    public static String md52(String str) {
        return md5(md5(str));
    }

    public static String md5(String str) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return buf.toString().substring(8, 24);
    }

    public static String randomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH时mm分");//设置日期格式
        return dateFormat.format(new Date());

    }


}
