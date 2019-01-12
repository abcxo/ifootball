package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.main.DiscoverUserFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.HomeTweetFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.views.IconFontView;
import com.abcxo.android.ifootball.views.ViewPager;

import java.util.ArrayList;

public class TabNavFragment extends NavFragment {
    private static final int TAB_CNT = 5;
    private int currentIndex;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static TabNavFragment newInstance() {
        return newInstance(null);
    }

    public static TabNavFragment newInstance(Bundle args) {
        TabNavFragment fragment = new TabNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_nav, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
        mFragments.add(HomeTweetFragment.newInstance(bundle));
        mFragments.add(HomeTweetFragment.newInstance(bundle));
        mFragments.add(HomeTweetFragment.newInstance(bundle));
        mFragments.add(SearchNavFragment.newInstance(bundle));
        mFragments.add(DiscoverUserFragment.newInstance(bundle));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setScrollable(false);
        viewPager.setOffscreenPageLimit(TAB_CNT);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));  // 设置tab图片和文字
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).getCustomView().setSelected(true);

    }


    public class TabAdapter extends FragmentPagerAdapter {



        public TabAdapter(FragmentManager fm, Context context) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


        @Override
        public int getCount() {
            return TAB_CNT;
        }

        public View getTabView(int position) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
            IconFontView ifv_tab = (IconFontView) tab.findViewById(R.id.ifv_tab);
            switch (position) {
                case 0:
                    ifv_tab.setText(R.string.iconfont_follow);
                    break;
                case 1:
                    ifv_tab.setText(R.string.iconfont_team);
                    break;
                case 2:
                    ifv_tab.setText(R.string.iconfont_video);
                    break;
                case 3:
                    ifv_tab.setText(R.string.iconfont_data);
                    break;
                case 4:
                    ifv_tab.setText(R.string.iconfont_data);
                    break;
            }
            return tab;
        }
    }

}

