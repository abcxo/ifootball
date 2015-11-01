package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.adapters.MainViewPagerAdapter;

public class MainFragment extends NavFragment {

    public static MainFragment newInstance() {
        return newInstance(null);
    }

    public static MainFragment newInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainViewPagerAdapter(getFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewPager);

    }


}
