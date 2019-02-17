package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class SettingNavFragment extends NavFragment {
    public static SettingNavFragment newInstance() {
        return newInstance(null);
    }

    public static SettingNavFragment newInstance(Bundle args) {
        SettingNavFragment fragment = new SettingNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNavActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
        getNavActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getNavActivity().getSupportActionBar().setDisplayUseLogoEnabled(true);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, SettingFragment.newInstance(getArguments()))
                .commit();
    }



}
