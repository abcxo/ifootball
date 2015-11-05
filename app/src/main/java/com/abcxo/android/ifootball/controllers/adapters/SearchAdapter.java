package com.abcxo.android.ifootball.controllers.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchUserFragment;

import static com.abcxo.android.ifootball.controllers.adapters.SearchAdapter.PageType.TWEET;
import static com.abcxo.android.ifootball.controllers.adapters.SearchAdapter.PageType.USER;

/**
 * Created by shadow on 15/11/1.
 */
public class SearchAdapter extends FragmentPagerAdapter {

    //获取用户列表
    public enum PageType {

        USER(0),
        TWEET(1);
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

    public SearchAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.search_page_list);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == USER.getIndex()) {
            return SearchUserFragment.newInstance();
        } else if (position == TWEET.getIndex()) {
            return SearchTweetFragment.newInstance();
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
