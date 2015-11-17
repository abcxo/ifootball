package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTweetFragment;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.List;

/**
 * Created by shadow on 15/11/17.
 */
public class AddTweetImageAdapter extends RecyclerView.Adapter<AddTweetImageAdapter.BindingHolder> {

    public List<Image> images;

    private AddTweetFragment.BindingHandler handler;

    public AddTweetImageAdapter(List<Image> images, AddTweetFragment.BindingHandler handler) {
        this.images = images;
        this.handler = handler;
    }

    public void addImage(Bitmap bitmap) {
        String url = FileUtils.saveImage(bitmap, Constants.DIR_ADD_TWEET, Utils.randomString());
        if (!TextUtils.isEmpty(url)) {
            Image image = new Image();
            image.url = url;
            images.add(image);
            notifyItemInserted(images.size());
        } else {
            ViewUtils.toast(R.string.add_tweet_send_image_error);
        }


    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;
        public View view;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
            view = rowView;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        BindingHolder holder = new BindingHolder(getItemLayoutView(parent, type));
        return holder;
    }

    public View getItemLayoutView(ViewGroup parent, int type) {
        if (type == ImageType.NORMAL.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_normal, parent, false);
        } else {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_add, parent, false);
        }

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ImageType.NORMAL.getIndex()) {
            final Image image = images.get(position);
            holder.binding.setVariable(BR.image, image);
            holder.view.setTag(image);
        }
        holder.binding.setVariable(BR.handler, handler);

    }


    @Override
    public int getItemViewType(int position) {
        if (position < images.size()) {
            return ImageType.NORMAL.getIndex();
        } else {
            return ImageType.ADD.getIndex();
        }
    }


    @Override
    public int getItemCount() {
        return images.size() + 1;
    }

    public enum ImageType {
        NORMAL(0),
        ADD(1);
        private int index;

        ImageType(int index) {
            this.index = index;
        }

        public static int size() {
            return ImageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


}
