package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.activities.AddTeamActivity;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.SettingActivity;
import com.abcxo.android.ifootball.controllers.fragments.main.DiscoverUserFragment;
import com.abcxo.android.ifootball.databinding.FragmentDiscoverNavBinding;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;

import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.DATA;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.HOME;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.NEWS;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.TEAM;

public class DiscoverNavFragment extends NavFragment {
    public static DiscoverNavFragment newInstance() {
        return newInstance(null);
    }

    public static DiscoverNavFragment newInstance(Bundle args) {
        DiscoverNavFragment fragment = new DiscoverNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDiscoverNavBinding binding = DataBindingUtil.bind(view);
        binding.setHandler(new BindingHandler());
        getNavActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, DiscoverUserFragment.newInstance(getArguments()))
                .commit();
    }


    public class BindingHandler {

        public void onClickNotification(View view) {
            NavUtils.toMessage(view.getContext());
        }

        public void onClickSetting(View view) {
            Intent intent = new Intent();
            intent.setClass(view.getContext(), SettingActivity.class);
            startActivity(intent);
        }
    }


}
