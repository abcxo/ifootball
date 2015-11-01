package com.abcxo.android.ifootball.controllers.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.main.DiscoverFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.HomeFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.NewsFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.TeamFragment;

/**
 * Created by shadow on 15/11/1.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] titles;

    public MainViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        titles = context.getResources().getStringArray(R.array.main_page_list);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return TeamFragment.newInstance();
            case 2:
                return NewsFragment.newInstance();
            case 3:
                return DiscoverFragment.newInstance();
            default:
                return null;


        }

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
