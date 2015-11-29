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
import com.abcxo.android.ifootball.controllers.fragments.add.AddTeamFragment;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTweetFragment;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.List;

/**
 * Created by shadow on 15/11/17.
 */
public class AddTeamFocusAdapter extends RecyclerView.Adapter<AddTeamFocusAdapter.BindingHolder> {

    public List<User> users;

    private AddTeamFragment.BindingHandler handler;

    public AddTeamFocusAdapter(List<User> users, AddTeamFragment.BindingHandler handler) {
        this.users = users;
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

    public View getItemLayoutView(ViewGroup parent, int type) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_team_focus, parent, false);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final User user = users.get(position);
        holder.binding.setVariable(BR.user, user);
        holder.binding.setVariable(BR.handler, handler);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
