package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.SignActivity;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.SelectIconFontView;
import com.abcxo.android.ifootball.views.ViewPager;

import java.util.ArrayList;

public class TabNavFragment extends NavFragment {
    private static final int TAB_CNT = 5;
    private int currentIndex;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;

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

        mFragments.add(HomeNavFragment.newInstance(bundle));
        mFragments.add(ProNavFragment.newInstance(bundle));
        mFragments.add(EmptyNavFragment.newInstance(bundle));
        mFragments.add(SearchNavFragment.newInstance(bundle));
        mFragments.add(SettingNavFragment.newInstance(bundle));

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
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

        adapter = new TabAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));  // 设置tab图片和文字
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 2) {
                    startActivityForResult(new Intent(getActivity(), AddTweetActivity.class), Constants.REQUEST_ADD);
                } else if (position == 4 && !UserRestful.INSTANCE.isLogin()) {
                    startActivityForResult(new Intent(getActivity(), SignActivity.class), Constants.REQUEST_SIGN);
                } else {
                    viewPager.setCurrentItem(position, false);
                    currentIndex = position;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_REFRESH_HOME));
                } else if (position == 1) {
                    LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_REFRESH_PRO));
                }
            }
        });
        tabLayout.getTabAt(0).getCustomView().setSelected(true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tabLayout.getTabAt(currentIndex).select();
        ViewUtils.closeKeyboard(getActivity());
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
            SelectIconFontView ifv_tab = (SelectIconFontView) tab.findViewById(R.id.ifv_tab);
            switch (position) {
                case 0:
                    ifv_tab.setText(R.string.iconfont_tab_home, R.string.iconfont_tab_home_select);
                    break;
                case 1:
                    ifv_tab.setText(R.string.iconfont_tab_pro, R.string.iconfont_tab_pro_select);
                    break;
                case 2:
                    ifv_tab.setText(R.string.iconfont_tab_add, R.string.iconfont_tab_add_select);
                    break;
                case 3:
                    ifv_tab.setText(R.string.iconfont_tab_search, R.string.iconfont_tab_search_select);
                    break;
                case 4:
                    ifv_tab.setText(R.string.iconfont_tab_me, R.string.iconfont_tab_me_select);
                    break;
            }
            return tab;
        }
    }

}

