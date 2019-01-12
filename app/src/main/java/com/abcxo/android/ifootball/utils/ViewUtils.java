package com.abcxo.android.ifootball.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shadow on 15/11/7.
 */
public class ViewUtils {

    private static ProgressDialog progressDialog;

    public static Uri imageUrl;

    public static int screenWidth() {
        Context context = Application.INSTANCE;
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT ? dm.widthPixels : dm.heightPixels;
    }

    public static int screenHeight() {
        Context context = Application.INSTANCE;
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT ? dm.heightPixels : dm.widthPixels;
    }

    public static int screenDensity() {
        Context context = Application.INSTANCE;
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) dm.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = Application.INSTANCE.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = Application.INSTANCE.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static void toast(String str) {
        Toast.makeText(Application.INSTANCE, str, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int resId) {
        Toast.makeText(Application.INSTANCE, resId, Toast.LENGTH_SHORT).show();
    }

    public static void loading(Context context) {
        try {
            dismiss();
            progressDialog = ProgressDialog.show(context, null, null);
        } catch (Throwable e) {

        }
    }

    public static void dismiss() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Throwable e) {

        }

    }


    public static void camera(Fragment fragment) {
        try {
            File dirFile = new File(Constants.DIR_TWEET_CACHE);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            imageUrl = Uri.fromFile(new File(Constants.DIR_TWEET_CACHE + Utils.randomString() + ".jpg"));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
            fragment.startActivityForResult(intent, Constants.REQUEST_CAMERA);
        } catch (Throwable e) {
            ViewUtils.toast(R.string.error_camera);
        }
    }

    public static void photo(Fragment fragment) {
        try {
            Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            fragment.startActivityForResult(picture, Constants.REQUEST_PHOTO);
        } catch (Throwable e) {
            ViewUtils.toast(R.string.error_photo);
        }

    }

    public static void image(final Fragment fragment) {
        new AlertDialog.Builder(fragment.getActivity())
                .setItems(R.array.image_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            camera(fragment);
                        } else {
                            photo(fragment);
                        }
                        dialog.dismiss();
                    }
                }).show();
    }


    public static void closeKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static void openKeyboard(Activity context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.requestFocus();
        imm.showSoftInput(editText, 0);
    }

    public static String getString(int resId) {
        return Application.INSTANCE.getString(resId);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 720, 1080);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }


    public static SpannableString getPromptString(String str) {
        if (!TextUtils.isEmpty(str)) {
            SpannableString spannableString = new SpannableString(str);
            Pattern pattern = Pattern.compile("@[^\\p{P}|\\p{S}|\\p{Z}|\\p{M}]*");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                spannableString.setSpan(new ForegroundColorSpan(Application.INSTANCE.getResources().getColor(R.color.color_button_accent)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;

        } else {
            return new SpannableString("");
        }
    }


    public static void copy(String str) {
        ClipboardManager clipboardManager = (ClipboardManager) Application.INSTANCE.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(str);
        ViewUtils.toast(str);
    }

}
