package com.abcxo.android.ifootball.controllers.fragments.add;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentAddTeamBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class AddTeamFragment extends Fragment {
    public static AddTeamFragment newInstance() {
        return newInstance(null);
    }

    public static AddTeamFragment newInstance(Bundle args) {
        AddTeamFragment fragment = new AddTeamFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    private TabLayout tabLayout;
    private ViewPager viewPager;

    private RecyclerView recyclerView;
    private AddTeamFocusAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_team, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAddTeamBinding binding = DataBindingUtil.bind(view);
        BindingHandler handler = new BindingHandler();
        binding.setHandler(handler);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(6);

        viewPager.setAdapter(new AddTeamAdapter(getChildFragmentManager(), getActivity(), handler));

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddTeamFocusAdapter(new ArrayList<User>(), handler);
        recyclerView.setAdapter(adapter);

    }


    public class AddTeamAdapter extends FragmentPagerAdapter {

        private String[] titles;

        private AddTeamFragment.BindingHandler handler;

        public AddTeamAdapter(FragmentManager fm, Context context, AddTeamFragment.BindingHandler handler) {
            super(fm);
            this.handler = handler;
            titles = context.getResources().getStringArray(R.array.team_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            String title = titles[position];
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_NAME, title);
            TeamFragment fragment = TeamFragment.newInstance(bundle);
            fragment.setListener(new TeamFragment.Listener() {
                @Override
                public void onLoaded(List<User> users) {
                    handler.onLoaded(users);
                }

                @Override
                public void onItemClick(View view, User user, int position) {
                    handler.onClickItem(view);
                }
            });
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }


    }


    public class AddTeamFocusAdapter extends RecyclerView.Adapter<AddTeamFocusAdapter.BindingHolder> {

        public List<User> users;

        private AddTeamFragment.BindingHandler handler;

        public AddTeamFocusAdapter(List<User> users, AddTeamFragment.BindingHandler handler) {
            this.users = users;
            this.handler = handler;
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
                    .inflate(R.layout.item_add_team_focus, parent, false);

        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            final User user = users.get(position);
            holder.binding.setVariable(BR.user, user);
            holder.binding.setVariable(BR.handler, handler);

        }

        @Override
        public int getItemCount() {
            return users.size();
        }


    }


    public class BindingHandler {

        public void onClickOk(final View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                ViewUtils.loading(view.getContext());
                UserRestful.INSTANCE.focusTeams(adapter.users, new UserRestful.OnUserRestfulDo() {
                    @Override
                    public void onSuccess() {
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_REFRESH_TEAM));
                        ViewUtils.dismiss();
                        getActivity().finish();
                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.dismiss();
                        ViewUtils.toast(error.msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
            } else {
                NavUtils.toSign(view.getContext());
            }
        }

        public void onClickFocus(final View view) {
            //do nothing
        }

        public void onClickItem(final View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            User user = (User) binding.getRoot().getTag();
            toggleFocusTeam(user);
        }

        public void onLoaded(List<User> users) {
            for (User user : users) {
                if (user.focus) {
                    adapter.users.add(user);
                    adapter.notifyItemInserted(adapter.users.size());
                }
            }
        }


    }

    private void toggleFocusTeam(User user) {
        if (adapter.users.contains(user)) {
            user.focus = false;
            user.notifyPropertyChanged(BR.focus);
            int pos = adapter.users.indexOf(user);
            adapter.users.remove(pos);
            adapter.notifyItemRemoved(pos);
        } else {
            user.focus = true;
            user.notifyPropertyChanged(BR.focus);
            adapter.users.add(user);
            adapter.notifyItemInserted(adapter.users.size());
        }
    }

}
