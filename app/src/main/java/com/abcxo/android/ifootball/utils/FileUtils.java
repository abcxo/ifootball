package com.abcxo.android.ifootball.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.abcxo.android.ifootball.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by shadow on 15/11/17.
 */
public class FileUtils {
    public static boolean setObject(String key, Serializable object) {
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

    public static Serializable getObject(String key) {
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

    public static boolean deleteObject(String key) {
        return Application.INSTANCE.deleteFile(key);

    }


    public static String saveImage(Bitmap bitmap, String dir, String name) {
        try {
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(dir + name + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.WEBP, 10, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {

        }
        return null;
    }

    public static boolean delete(String path) {
        File dirFile = new File(path);
        return dirFile.delete();
    }

}
