package com.abcxo.android.ifootball.controllers.adapters.slidebar;

import android.view.View;
import android.widget.TextView;

import com.abcxo.android.ifootball.databinding.ItemSlidebarChildrenBinding;
import com.abcxo.android.ifootball.models.User;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

public class ChildrenViewHolder extends ChildViewHolder {

    private ItemSlidebarChildrenBinding binding;
    private TextView tv_name;
    private RoundedImageView riv_avatar;

    public ChildrenViewHolder(View itemView) {
        super(itemView);

        binding = ItemSlidebarChildrenBinding.bind(itemView);
//        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
//        riv_avatar = (RoundedImageView) itemView.findViewById(R.id.riv_avatar);
    }

    public void bind(User user) {
        binding.setUser(user);
//        tv_name.setText(user.name);
//        riv_avatar.setImageURI(Uri.parse(user.avatar));
    }
}
