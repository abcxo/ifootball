package com.abcxo.android.ifootball.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;

/**
 * Created by shadow on 15/11/7.
 */
public class ViewUtils {

    private static ProgressDialog progressDialog;

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
        } catch (Exception e) {

        }
    }

    public static void dismiss() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {

        }

    }


    public static void camera(Fragment fragment) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fragment.startActivityForResult(intent, Constants.REQUEST_CAMERA);
        } catch (Exception e) {
            ViewUtils.toast(R.string.error_camera);
        }
    }

    public static void photo(Fragment fragment) {
        try {
            Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            fragment.startActivityForResult(picture, Constants.REQUEST_PHOTO);
        } catch (Exception e) {
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


}
