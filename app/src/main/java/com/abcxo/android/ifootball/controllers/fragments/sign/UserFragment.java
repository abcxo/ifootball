package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.app.Activity;
import android.content.Intent;
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
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.DividerItemDecoration;
import com.abcxo.android.ifootball.views.RecyclerItemClickListener;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.abcxo.android.ifootball.models.User.UserMainType.CONTACT;
import static com.abcxo.android.ifootball.models.User.UserMainType.DISCOVER;

public class UserFragment extends Fragment {

    protected List<User> list = new ArrayList<>();

    protected SuperRecyclerView recyclerView;
    protected UserAdapter adapter;

    protected long uid;
    protected boolean isSelect;

    protected int pageIndex;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;

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
            isSelect = args.getBoolean(Constants.KEY_IS_SELECT);
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


        if (isSelect) {
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            User user = adapter.users.get(position);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constants.KEY_USER, user);
                            intent.putExtras(bundle);
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                    })
            );
        }

    }

    public boolean needFirstRefresh() {
        return true;
    }


    protected void load() {
        ArrayList<User> users = (ArrayList<User>) FileUtils.getObject(getKey());
        if (users != null && users.size() > 0) {
            refreshUsers(users);
        }
        refresh();

    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;getsType=%s;", uid, getGetsType().name()));
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
        UserRestful.INSTANCE.gets(getGetsType(), uid, getKeyword(), pageIndex, new UserRestful.OnUserRestfulList() {
            @Override
            public void onSuccess(List<User> users) {
                if (first) {
                    refreshUsers(users);
                    FileUtils.setObject(getKey(), new ArrayList<>(users));
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

    protected String getKeyword() {
        return "";
    }


    protected void refreshUsers(List<User> users) {
        list.clear();
        if (users != null && users.size() > 0) {
            list.addAll(users);
            pageIndex++;
        }
        adapter.notifyDataSetChanged();

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


    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.BindingHolder> {

        public List<User> users;

        public UserAdapter(List<User> users) {
            this.users = users;
        }

        public class BindingHolder extends RecyclerView.ViewHolder {
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
            if (type == CONTACT.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_contact, parent, false);
            } else if (type == DISCOVER.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_discover, parent, false);
            } else {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_normal, parent, false);
            }
        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            final User user = users.get(position);
            holder.binding.setVariable(BR.user, user);
            int type = getItemViewType(position);
            if (type == CONTACT.getIndex()) {
                boolean showIndex = true;
                if (position > 0) {
                    User previousUser = users.get(position - 1);
                    if (user.letter.equals(previousUser.letter)) {
                        showIndex = false;
                    }
                }
                holder.binding.setVariable(BR.showIndex, showIndex);
                holder.binding.setVariable(BR.isSelect, isSelect);
            }
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
}
