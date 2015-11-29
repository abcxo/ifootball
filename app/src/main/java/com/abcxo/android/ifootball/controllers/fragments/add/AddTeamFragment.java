package com.abcxo.android.ifootball.controllers.fragments.add;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.AddTeamAdapter;
import com.abcxo.android.ifootball.controllers.adapters.AddTeamFocusAdapter;
import com.abcxo.android.ifootball.controllers.adapters.AddTweetImageAdapter;
import com.abcxo.android.ifootball.controllers.adapters.SearchAdapter;
import com.abcxo.android.ifootball.databinding.FragmentAddTeamBinding;
import com.abcxo.android.ifootball.databinding.FragmentAddTweetBinding;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
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
//        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    public class BindingHandler {

        public void onClickOk(final View view) {
            ViewUtils.loading(view.getContext());
            UserRestful.INSTANCE.focusTeams(adapter.users, new UserRestful.OnUserRestfulDo() {
                @Override
                public void onSuccess() {
                    getActivity().finish();
                }

                @Override
                public void onError(RestfulError error) {
                    ViewUtils.toast(error.msg);
                }

                @Override
                public void onFinish() {
                    ViewUtils.dismiss();
                }
            });
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
