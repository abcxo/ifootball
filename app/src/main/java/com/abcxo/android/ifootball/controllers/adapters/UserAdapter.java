package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.models.User;

import java.util.List;

import static com.abcxo.android.ifootball.models.UserMainType.CONTACT;
import static com.abcxo.android.ifootball.models.UserMainType.DISCOVER;
import static com.abcxo.android.ifootball.models.UserMainType.NORMAL;
import static com.abcxo.android.ifootball.models.UserMainType.SPECIAL;

/**
 * Created by shadow on 15/11/4.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.BindingHolder> {

    public List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        BindingHolder holder = new BindingHolder(getItemLayoutView(parent, type));
        return holder;
    }

    public View getItemLayoutView(ViewGroup parent, int type) {
        if (type == NORMAL.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_normal, parent, false);
        } else if (type == CONTACT.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_contact, parent, false);
        } else if (type == DISCOVER.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_discover, parent, false);
        } else if (type == SPECIAL.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_special, parent, false);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final User user = users.get(position);
        holder.binding.setVariable(BR.user, user);

    }


    @Override
    public int getItemViewType(int position) {
        User user = users.get(position);
        return user.mainType.getIndex();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
