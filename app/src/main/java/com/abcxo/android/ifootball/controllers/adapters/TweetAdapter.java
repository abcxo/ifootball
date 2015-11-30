package com.abcxo.android.ifootball.controllers.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.models.Tweet;

import java.util.List;

import static com.abcxo.android.ifootball.models.Tweet.TweetType.NEWS;
import static com.abcxo.android.ifootball.models.Tweet.TweetType.TEAM;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.BindingHolder> {

    private List<Tweet> tweets;


    public TweetAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
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
        if (type == TEAM.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tweet_team, parent, false);
        } else if (type == NEWS.getIndex()) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tweet_news, parent, false);
        }else{
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tweet_normal, parent, false);
        }

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        holder.binding.setVariable(BR.tweet, tweet);
    }


    @Override
    public int getItemViewType(int position) {
        Tweet tweet = tweets.get(position);
        return tweet.tweetType.getIndex();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

}
