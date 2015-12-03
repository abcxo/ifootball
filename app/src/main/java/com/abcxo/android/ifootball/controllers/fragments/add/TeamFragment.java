package com.abcxo.android.ifootball.controllers.fragments.add;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.TeamAdapter;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {


    protected String name;

    protected SuperRecyclerView recyclerView;

    protected TeamAdapter adapter;
    private Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public static TeamFragment newInstance() {
        return newInstance(null);
    }

    public static TeamFragment newInstance(Bundle args) {
        TeamFragment fragment = new TeamFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString(Constants.KEY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TeamAdapter(new ArrayList<User>(), new BindingHandler());
        recyclerView.setAdapter(adapter);

        recyclerView.setRefreshingColorResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);
        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        };
        recyclerView.setRefreshListener(onRefreshListener);

        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getSwipeToRefresh().setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });

    }

    protected void loadData(final boolean first) {
        UserRestful.INSTANCE.getTeams(name, new UserRestful.OnUserRestfulList() {
            @Override
            public void onSuccess(List<User> users) {
                if (first) {
                    refreshUsers(users);
                    if (TeamFragment.this.listener != null) {
                        TeamFragment.this.listener.onLoaded(users);
                    }
                    recyclerView.getSwipeToRefresh().setEnabled(false);
                } else {
                    addUsers(users);
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


    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.USER;
    }


    protected void refreshUsers(List<User> users) {

        adapter.users.clear();
        if (users != null && users.size() > 0) {
            adapter.users.addAll(users);
        }
        adapter.notifyDataSetChanged();

    }

    protected void addUsers(List<User> users) {
        int bCount = adapter.users.size();
        adapter.users.addAll(users);
        adapter.notifyItemRangeInserted(bCount, users.size());
    }


    public class BindingHandler {
        public void onClickItem(View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            User user = (User) binding.getRoot().getTag();
            if (listener != null) {
                listener.onItemClick(view, user, adapter.users.indexOf(user));
            }
        }
    }

    public interface Listener {
        void onLoaded(List<User> users);

        void onItemClick(View view, User user, int position);
    }

}
