package com.abcxo.android.ifootball.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
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
import java.util.regex.Pattern;

/**
 * Created by shadow on 15/11/14.
 */
public class Utils {

    public static void registerLogin(BroadcastReceiver receiver) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_LOGIN));
    }

    public static void registerLogout(BroadcastReceiver receiver) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_LOGOUT));
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


    public static boolean set(String key, Serializable object) {
        try {
            FileOutputStream fos = Application.INSTANCE.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Serializable get(String key) {
        try {
            FileInputStream fis = Application.INSTANCE.openFileInput(key);
            ObjectInputStream is = new ObjectInputStream(fis);
            Serializable object = (Serializable) is.readObject();
            is.close();
            fis.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    public static boolean delete(String key) {
        return Application.INSTANCE.deleteFile(key);

    }

}
