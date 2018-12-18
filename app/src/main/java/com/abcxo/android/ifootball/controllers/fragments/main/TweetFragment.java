package com.abcxo.android.ifootball.controllers.fragments.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.ItemBannerBinding;
import com.abcxo.android.ifootball.models.Game;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.GameRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.DividerItemDecoration;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class TweetFragment extends Fragment {

    protected List<Tweet> mTweetList = new ArrayList<>();
    protected List<Game> mGameList = new ArrayList<>();
    protected long uid;

    protected SuperRecyclerView recyclerView;
    protected TweetAdapter adapter;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    protected int pageIndex;

    protected BannerAdapter bannerAdapter;

    public static TweetFragment newInstance() {
        return newInstance(null);
    }

    public static TweetFragment newInstance(Bundle args) {
        TweetFragment fragment = new TweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uid = args.getLong(Constants.KEY_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), true, DividerItemDecoration.VERTICAL));

        adapter = new TweetAdapter(mTweetList);
        recyclerView.setAdapter(adapter);

        recyclerView.setRefreshingColorResources(R.color.color_primary, R.color.color_primary, R.color.color_primary, R.color.color_primary);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                loadData(true);
            }
        };
        recyclerView.setRefreshListener(onRefreshListener);

        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                loadData(false);
            }
        }, Constants.MAX_LEFT_MORE);
        if (needFirstRefresh()) {
            load();
        }
    }

    public void scrollToTopAndRefresh() {
        if (recyclerView != null) {
            recyclerView.getRecyclerView().scrollToPosition(0);
            refresh();
        }
    }

    protected void load() {
        ArrayList<Tweet> tweets = (ArrayList<Tweet>) FileUtils.getObject(getKey());
        if (tweets != null && tweets.size() > 0) {
            refreshTweets(tweets);
        }
        refresh();
    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;getsType=%s;", uid, getGetsType().name()));
    }

    public boolean needFirstRefresh() {
        return true;
    }

    public void refresh() {
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getSwipeToRefresh().setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
    }

    protected void loadData(final boolean first) {
        if (first && getGetsType() == TweetRestful.GetsType.HOME) {
            loadLiveData();
        } else {
            loadTweetData(first);
        }
    }

    // 加载直播数据
    private void loadLiveData() {
        GameRestful.INSTANCE.gets(uid,
                0, new GameRestful.OnGameRestfulList() {
                    @Override
                    public void onSuccess(List<Game> games) {
                        mGameList.clear();
                        if (games != null && games.size() > 0) {
                            // 拿到最多四条直播数据
                            for (int i = 0; i < games.size(); i++) {
                                if (i < 4) {
                                    mGameList.add(games.get(i));
                                } else {
                                    break;
                                }
                            }
                        }

                        loadTweetData(true);
                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.toast(error.msg);
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    // 加载Tweet数据
    private void loadTweetData(final boolean first) {
        TweetRestful.INSTANCE.gets(getGetsType(), uid, getKeyword(),
                pageIndex, new TweetRestful.OnTweetRestfulList() {
                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        if (first) {
                            refreshTweets(tweets);
                            FileUtils.setObject(getKey(), new ArrayList<>(tweets));
                        } else {
                            addTweets(tweets);
                        }

                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.toast(error.msg);
                    }

                    @Override
                    public void onFinish() {
                        if (first) {
                            recyclerView.getSwipeToRefresh().setRefreshing(false);
                        } else {
                            recyclerView.hideMoreProgress();
                        }
                    }
                });
    }

    protected String getKeyword() {
        return "";
    }

    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.USER;
    }

    protected void refreshTweets(List<Tweet> tweets) {
        mTweetList.clear();

        if (tweets != null && tweets.size() > 0) {
            mTweetList.addAll(tweets);
            pageIndex++;
        }
        adapter.notifyDataSetChanged();
    }


    protected void addTweets(List<Tweet> tweets) {
        if (tweets != null && tweets.size() > 0) {
            int bCount = mTweetList.size();
            mTweetList.addAll(tweets);
            adapter.notifyItemRangeInserted(bCount, tweets.size());
            pageIndex++;
        }
    }

    public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.BindingHolder> {

        private List<Tweet> tweetsList;

        private int BANNER_INDEX = 0x99;

        public TweetAdapter(List<Tweet> tweetsList) {
            this.tweetsList = tweetsList;
        }

        public class BindingHolder extends RecyclerView.ViewHolder {
            public ViewDataBinding viewDataBinding;
            public View view;

            public BindingHolder(View rowView, int type) {
                super(rowView);
                if (type != BANNER_INDEX) {
                    viewDataBinding = DataBindingUtil.bind(rowView);
                }
                view = rowView;
            }
        }

        @Override
        public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
            BindingHolder holder = new BindingHolder(getItemLayoutView(parent, type), type);
            return holder;
        }

        public View getItemLayoutView(ViewGroup parent, int type) {

            if (type == BANNER_INDEX) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet_banner, parent, false);
            } else if (type == Tweet.TweetMainType.TEAM.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet_team, parent, false);
            } else if (type == Tweet.TweetMainType.NEWS.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet_news, parent, false);
            } else if (type == Tweet.TweetMainType.IMAGE.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet_image, parent, false);
            } else {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet_normal, parent, false);
            }
        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            //TODO:shadow
//            if (getGetsType() == TweetRestful.GetsType.HOME) {
//                if (position == 0) {
//                    // 宽高比 4 : 1
//                    int screen_width = ViewUtils.screenWidth();
//                    holder.view.getLayoutParams().height = screen_width / 4;
//                    final ViewPager viewPager = (ViewPager) holder.view.findViewById(R.id.view_pager);
//                    if (bannerAdapter == null) {
//                        bannerAdapter = new BannerAdapter();
//                        viewPager.setAdapter(bannerAdapter);
//                    } else {
//                        bannerAdapter.notifyDataSetChanged();
//                    }
//                } else {
//                    final Tweet tweet = tweetsList.get(position - 1);
//                    holder.viewDataBinding.setVariable(BR.tweet, tweet);
//                }
//
//            } else {
                final Tweet tweet = tweetsList.get(position);
                holder.viewDataBinding.setVariable(BR.tweet, tweet);
//            }
        }

        @Override
        public int getItemViewType(int position) {
            //TODO:shadow
//            if (getGetsType() == TweetRestful.GetsType.HOME) {
//                if (position == 0) {
//                    return BANNER_INDEX;
//                } else {
//                    Tweet tweet = tweetsList.get(position - 1);
//                    return tweet.getMainType().getIndex();
//                }
//            } else {
                Tweet tweet = tweetsList.get(position);
                return tweet.getMainType().getIndex();
//            }


        }

        @Override
        public int getItemCount() {
            //TODO:shadow
//            if (getGetsType() == TweetRestful.GetsType.HOME) {
//                return 1 + tweetsList.size();
//            }
            return tweetsList.size();
        }
    }

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mGameList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ItemBannerBinding layout = DataBindingUtil.inflate(LayoutInflater.from(getContext())
                    , R.layout.item_banner, container, false);
            ViewDataBinding binding = DataBindingUtil.bind(layout.getRoot());
            Game game = mGameList.get(position);
            binding.setVariable(BR.game, game);

            View root = layout.getRoot();

            container.addView(root);

//            switch (position) {
//                case 0:
//                    root.setBackgroundResource(R.drawable.banner1);
//                    break;
//                case 1:
//                    root.setBackgroundResource(R.drawable.banner2);
//                    break;
//                case 2:
//                    root.setBackgroundResource(R.drawable.banner3);
//                    break;
//                case 3:
//                    root.setBackgroundResource(R.drawable.banner4);
//                    break;
//            }

            return root;

//            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, container, false);
//            container.addView(view);
//            return view;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
