package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.UserAdapter;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.DividerItemDecoration;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {


    protected List<User> list = new ArrayList<>();

    protected SuperRecyclerView recyclerView;
    protected UserAdapter adapter;

    protected long uid;

    protected int pageIndex;

    public static UserFragment newInstance() {
        return newInstance(null);
    }

    public static UserFragment newInstance(Bundle args) {
        UserFragment fragment = new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
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

        adapter = new UserAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.setRefreshingColorResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);
        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
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
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getSwipeToRefresh().setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });


    }


    protected void loadData(final boolean first) {
        UserRestful.INSTANCE.gets(uid, getGetsType(), pageIndex, new UserRestful.OnUserRestfulList() {
            @Override
            public void onSuccess(List<User> users) {
                if (first) {
                    refreshUsers(users);
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


    protected void refreshUsers(List<User> users) {
        if (users != null && users.size() > 0) {
            list.clear();
            list.addAll(users);
            adapter.notifyDataSetChanged();
            pageIndex++;
        }
    }

    protected void addUsers(List<User> users) {
        if (users != null && users.size() > 0) {
            int bCount = list.size();
            list.addAll(users);
            adapter.notifyItemRangeInserted(bCount, users.size());
            pageIndex++;
        }
    }

    protected UserRestful.GetsType getGetsType() {
        return UserRestful.GetsType.DISCOVER;
    }

}
