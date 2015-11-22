package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.detail.UserImageFragment;
import com.abcxo.android.ifootball.controllers.fragments.detail.UserImageFragment.BindingHandler;
import com.abcxo.android.ifootball.models.Image;

import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class UserImageAdapter extends RecyclerView.Adapter<UserImageAdapter.BindingHolder> {

    public List<Image> images;
    public BindingHandler handler;

    public UserImageAdapter(List<Image> images, UserImageFragment.BindingHandler handler) {
        this.images = images;
        this.handler = handler;
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

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Image image = images.get(position);
        holder.binding.setVariable(BR.image, image);
        holder.binding.setVariable(BR.handler, handler);
        holder.view.setTag(image);
    }

    public View getItemLayoutView(ViewGroup parent, int type) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_user, parent, false);

    }



    @Override
    public int getItemCount() {
        return images.size();
    }

}
