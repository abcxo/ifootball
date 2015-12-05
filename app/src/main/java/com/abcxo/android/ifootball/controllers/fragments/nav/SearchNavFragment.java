package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchUserFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;

public class SearchNavFragment extends NavFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static SearchNavFragment newInstance() {
        return newInstance(null);
    }

    public static SearchNavFragment newInstance(Bundle args) {
        SearchNavFragment fragment = new SearchNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_nav, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(new SearchAdapter(getChildFragmentManager(), getActivity()));

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
    }



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

    public class SearchAdapter extends FragmentPagerAdapter {


        private String[] titles;

        public SearchAdapter(FragmentManager fm, Context context) {
            super(fm);
            titles = context.getResources().getStringArray(R.array.search_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            if (position == PageType.USER.getIndex()) {
                return SearchUserFragment.newInstance(bundle);
            } else if (position == PageType.TWEET.getIndex()) {
                return SearchTweetFragment.newInstance(bundle);
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



}
