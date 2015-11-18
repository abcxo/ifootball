package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.abcxo.android.ifootball.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by shadow on 15/11/1.
 */
public class BindingAdapter {

    @android.databinding.BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable error) {
        if (url.contains("http")) {
            Picasso.with(imageView.getContext()).load(url).placeholder(error).into(imageView);
        } else {
            Picasso.with(imageView.getContext()).load(new File(url)).placeholder(error).into(imageView);
        }
    }

    @android.databinding.BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")) {
                Picasso.with(imageView.getContext()).load(url).into(imageView);
            } else {
                Picasso.with(imageView.getContext()).load(new File(url)).into(imageView);
            }
        }

    }


    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }

}
