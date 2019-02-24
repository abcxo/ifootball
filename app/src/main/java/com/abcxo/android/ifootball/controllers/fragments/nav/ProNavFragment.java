package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.activities.DiscoverActivity;
import com.abcxo.android.ifootball.controllers.fragments.main.ProTweetFragment;
import com.abcxo.android.ifootball.databinding.FragmentProNavBinding;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.views.UIUtils;

public class ProNavFragment extends NavFragment {
    public static ProNavFragment newInstance() {
        return newInstance(null);
    }

    public static ProNavFragment newInstance(Bundle args) {
        ProNavFragment fragment = new ProNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pro_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNavActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
        getNavActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getNavActivity().getSupportActionBar().setDisplayUseLogoEnabled(true);
        FragmentProNavBinding binding = DataBindingUtil.bind(view);
        binding.setHandler(new BindingHandler());
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, ProTweetFragment.newInstance(getArguments()))
                .commit();
    }


    public class BindingHandler {

        public void onClickNotification(View view) {
            NavUtils.toMessage(view.getContext());
        }

        public void onClickDiscover(View view) {
            Intent intent = new Intent();
            intent.setClass(view.getContext(), DiscoverActivity.class);
            startActivity(intent);
        }
    }
}
