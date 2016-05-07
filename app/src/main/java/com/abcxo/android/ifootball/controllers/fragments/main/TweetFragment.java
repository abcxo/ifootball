package com.abcxo.android.ifootball.controllers.fragments.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.main.LiveFragment.GameSection;
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
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;


public class TweetFragment extends Fragment {

    protected List<Tweet> mTweetList = new ArrayList<>();
    protected List<LiveFragment.GameSection> mGameList = new ArrayList<>();
    protected long uid;

    protected SuperRecyclerView recyclerView;
    protected TweetAdapter adapter;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    protected int pageIndex;

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
                getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new TweetAdapter(mTweetList);
        recyclerView.setAdapter(adapter);

        recyclerView.setRefreshingColorResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);
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
                pageIndex, new GameRestful.OnGameRestfulList() {
                    @Override
                    public void onSuccess(List<Game> games) {
                        mGameList.clear();
                        List<GameSection> gameSections = gamesToGameSections(games);
                        if (gameSections != null && gameSections.size() > 0) {
                            // 拿到最多三条直播数据
                            for (int i = 0; i < gameSections.size(); i++) {
                                if (i < 3) {
                                    mGameList.add(gameSections.get(i));
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

        public TweetAdapter(List<Tweet> tweetsList) {
            this.tweetsList = tweetsList;
        }

        public class BindingHolder extends RecyclerView.ViewHolder {
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

            if (type == GameSection.GameSectionType.FOCUS.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_gamesection_focus, parent, false);
            } else if (type == GameSection.GameSectionType.NORMAL.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_gamesection_normal, parent, false);
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
            if (getGetsType() == TweetRestful.GetsType.HOME && position < mGameList.size()) {
                final GameSection gameSection = mGameList.get(position);
                holder.binding.setVariable(BR.gameSection, gameSection);
                boolean showIndex = true;
                if (position > 0) {
                    GameSection previousSection = mGameList.get(position - 1);
                    if (gameSection.title.equals(previousSection.title)) {
                        showIndex = false;
                    }
                }
                holder.binding.setVariable(BR.showIndex, showIndex);
            } else {
                final Tweet tweet = tweetsList.get(position);
                holder.binding.setVariable(BR.tweet, tweet);
            }
        }


        @Override
        public int getItemViewType(int position) {
            if (getGetsType() == TweetRestful.GetsType.HOME && position < mGameList.size()) {
                return mGameList.get(position).type.getIndex();
            }

            Tweet tweet = tweetsList.get(position);
            return tweet.getMainType().getIndex();
        }

        @Override
        public int getItemCount() {
            if (getGetsType() == TweetRestful.GetsType.HOME) {
                return mGameList.size() + tweetsList.size();
            }
            return tweetsList.size();
        }
    }

    private List<LiveFragment.GameSection> gamesToGameSections(List<Game> games) {
        List<GameSection> gameSections = new ArrayList<>();
        GameSection gameSection = null;
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (gameSection == null || !game.section.equals(gameSection.title) || gameSection.game2 != null || !game.focus) {
                if (gameSection != null) {
                    gameSections.add(gameSection);
                }
                gameSection = new GameSection();
                gameSection.game = game;
                if (game.focus) {
                    gameSection.title = game.section;
                    gameSection.type = GameSection.GameSectionType.FOCUS;
                } else {
                    gameSection.title = Utils.date(game.date);
                    game.time = Utils.time(game.date);
                    gameSection.type = GameSection.GameSectionType.NORMAL;
                }

            } else {
                gameSection.game2 = game;
            }
        }
        if (gameSection != null) {
            gameSections.add(gameSection);
        }
        return gameSections;
    }
}
