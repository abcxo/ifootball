package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.restfuls.UserRestful;

import java.util.List;

import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT_ME;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT_USER;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.COMMENT;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.COMMENT_TWEET;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.FOCUS;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.NORMAL;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.PROMPT;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.SPECIAL;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.STAR;

/**
 * Created by shadow on 15/11/4.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.BindingHolder> {

    public List<Message> messages;

    public MessageAdapter(List<Message> users) {
        this.messages = users;
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
                    .inflate(R.layout.item_message_normal, parent, false);
        } else if (type == FOCUS.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_focus, parent, false);
        } else if (type == COMMENT.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_comment, parent, false);
        } else if (type == PROMPT.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_prompt, parent, false);
        } else if (type == STAR.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_star, parent, false);
        } else if (type == CHAT.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_chat, parent, false);
        } else if (type == SPECIAL.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_special, parent, false);
        } else if (type == COMMENT_TWEET.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_comment_tweet, parent, false);
        } else if (type == CHAT_USER.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_chat_user, parent, false);
        } else if (type == CHAT_ME.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_chat_me, parent, false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Message message = messages.get(position);
        holder.binding.setVariable(BR.message, message);

    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        Message.MessageMainType type = message.mainType;
        if (type == Message.MessageMainType.CHAT_USER && message.uid == UserRestful.INSTANCE.meId()) {
            type = Message.MessageMainType.CHAT_ME;
        }

        return type.getIndex();


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
