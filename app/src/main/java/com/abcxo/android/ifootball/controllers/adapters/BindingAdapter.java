package com.abcxo.android.ifootball.controllers.adapters;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by shadow on 15/11/1.
 */
public class BindingAdapter {

    @android.databinding.BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(imageView.getContext()).load(url).into(imageView);
        }

    }




}
