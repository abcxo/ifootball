package com.abcxo.android.ifootball.controllers.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.main.DiscoverUserFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.HomeTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.NewsTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.TeamTweetFragment;

import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.DISCOVER;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.HOME;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.NEWS;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.TEAM;

/**
 * Created by shadow on 15/11/1.
 */
public class MainAdapter extends FragmentPagerAdapter {

    //获取用户列表
    public enum PageType {

        HOME(0),
        TEAM(1),
        NEWS(2),
        DISCOVER(3);
        private int index;

        PageType(int index) {
            this.index = index;
        }

        public static int size() {
            return PageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    private String[] titles;

    public MainAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.main_page_list);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == HOME.getIndex()) {
            return HomeTweetFragment.newInstance();
        } else if (position == TEAM.getIndex()) {
            return TeamTweetFragment.newInstance();
        } else if (position == NEWS.getIndex()) {
            return NewsTweetFragment.newInstance();
        } else if (position == DISCOVER.getIndex()) {
            return DiscoverUserFragment.newInstance();
        }
        return null;
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
