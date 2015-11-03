package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.databinding.ItemHomeBinding;
import com.abcxo.android.ifootball.models.Tweet;

import java.util.List;

/**
 * Created by shadow on 15/11/1.
 */
public class TweetRecyclerViewAdapter extends RecyclerView.Adapter<TweetRecyclerViewAdapter.BindingHolder> {

    public List<Tweet> tweets;

    public TweetRecyclerViewAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        public ItemHomeBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        BindingHolder holder = new BindingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        holder.binding.setTweet(tweet);
    }


    @Override
    public int getItemViewType(int position) {
        return 3;
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
