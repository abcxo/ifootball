package com.abcxo.android.ifootball.controllers.adapters;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.abcxo.android.ifootball.Application;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by shadow on 15/11/1.
 */
public class BindingAdapter {

    @android.databinding.BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")) {
                Picasso.with(Application.INSTANCE).load(url).placeholder(errorDrawable).into(imageView);
            } else {
                Picasso.with(Application.INSTANCE).load(new File(url)).placeholder(errorDrawable).into(imageView);
            }
        } else {
            imageView.setImageDrawable(errorDrawable);
        }

    }

    public static void loadImage(ImageView imageView, String url, int resId) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")) {
                Picasso.with(Application.INSTANCE).load(url).placeholder(resId).into(imageView);
            } else {
                Picasso.with(Application.INSTANCE).load(new File(url)).placeholder(resId).into(imageView);
            }
        } else {
            imageView.setImageResource(resId);
        }

    }

    @android.databinding.BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, null);

    }


}
