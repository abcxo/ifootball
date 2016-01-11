package com.abcxo.android.ifootball.controllers.fragments.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Game;
import com.abcxo.android.ifootball.restfuls.GameRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends Fragment {
    private BroadcastReceiver receiver;

    protected List<GameSection> list = new ArrayList<>();
    protected long uid;

    protected SuperRecyclerView recyclerView;
    protected LiveAdapter adapter;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    protected int pageIndex;


    public static LiveFragment newInstance() {
        return newInstance(null);
    }

    public static LiveFragment newInstance(Bundle args) {
        LiveFragment fragment = new LiveFragment();
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
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh();
            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_REFRESH_TEAM));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.unregisterReceiver(receiver);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LiveAdapter(list);
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
        ArrayList<Game> games = (ArrayList<Game>) FileUtils.getObject(getKey());
        if (games != null && games.size() > 0) {
            refreshGameSections(gamesToGameSections(games));
        }
        refresh();
    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;getsType=%s;", uid, "live"));
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
        GameRestful.INSTANCE.gets(uid,
                pageIndex, new GameRestful.OnGameRestfulList() {
                    @Override
                    public void onSuccess(List<Game> games) {
                        if (first) {
                            refreshGameSections(gamesToGameSections(games));
                            FileUtils.setObject(getKey(), new ArrayList<>(games));
                        } else {
                            addGameSections(gamesToGameSections(games));
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


    private List<GameSection> gamesToGameSections(List<Game> games) {
        List<GameSection> gameSections = new ArrayList<>();
        GameSection gameSection = null;
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (gameSection == null || !game.section.equals(gameSection.title) || gameSection.game2 != null) {
                if (gameSection != null) {
                    gameSections.add(gameSection);
                }
                gameSection = new GameSection();
                gameSection.game = game;
                gameSection.title = game.section;
            } else {
                gameSection.game2 = game;
            }
        }
        if (gameSection != null) {
            gameSections.add(gameSection);
        }
        return gameSections;
    }

    public static class GameSection {
        public String title;
        public Game game;
        public Game game2;
    }

    protected void refreshGameSections(List<GameSection> gameSections) {
        list.clear();


        if (gameSections != null && gameSections.size() > 0) {
            list.addAll(gameSections);
            pageIndex++;

        }
        adapter.notifyDataSetChanged();
    }


    protected void addGameSections(List<GameSection> gameSections) {
        if (gameSections != null && gameSections.size() > 0) {
            int bCount = list.size();
            list.addAll(gameSections);
            adapter.notifyItemRangeInserted(bCount, gameSections.size());
            pageIndex++;
        }
    }


    public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.BindingHolder> {

        private List<GameSection> gameSections;


        public LiveAdapter(List<GameSection> gameSections) {
            this.gameSections = gameSections;
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
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gamesection_normal, parent, false);
        }


        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            final GameSection gameSection = gameSections.get(position);
            holder.binding.setVariable(BR.gameSection, gameSection);
            boolean showIndex = true;
            if (position > 0) {
                GameSection previousSection = gameSections.get(position - 1);
                if (gameSection.title.equals(previousSection.title)) {
                    showIndex = false;
                }
            }
            holder.binding.setVariable(BR.showIndex, showIndex);
        }


        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public int getItemCount() {
            return gameSections.size();
        }

    }


}
