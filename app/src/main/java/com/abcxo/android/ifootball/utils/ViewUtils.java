package com.abcxo.android.ifootball.utils;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.sign.CompleteSignFragment;

/**
 * Created by shadow on 15/11/7.
 */
public class ViewUtils {

    private static ProgressDialog progressDialog;

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
        dismiss();
        progressDialog = ProgressDialog.show(context, null, null);
    }

    public static void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public static void camera(Fragment fragment) {

        if (ActivityCompat.checkSelfPermission(fragment.getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (fragment.shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                ViewUtils.toast(R.string.error_camera);
            } else {
                fragment.requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        Constants.REQUEST_PERMISSION_CAMERA);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fragment.startActivityForResult(intent, Constants.REQUEST_CAMERA);
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


    public static String getString(int resId) {
        return Application.INSTANCE.getString(resId);
    }


}
