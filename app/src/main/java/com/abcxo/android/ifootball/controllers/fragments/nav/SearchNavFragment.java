package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchUserFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.ViewUtils;

public class SearchNavFragment extends NavFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText inputET;
    private SearchTweetFragment searchTweetFragment;
    private SearchUserFragment searchUserFragment;

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
        getNavActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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

        inputET = (EditText) view.findViewById(R.id.input);
        inputET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String keyword = inputET.getText().toString().trim();
                    searchUserFragment.keyword = keyword;
                    searchTweetFragment.keyword = keyword;
                    if (!TextUtils.isEmpty(keyword)) {
                        searchUserFragment.refresh();
                        searchTweetFragment.refresh();
                        ViewUtils.closeKeyboard(getActivity());
                    } else {
                        ViewUtils.toast(R.string.error_search_empty);
                    }
                    return true;
                }
                return false;
            }
        });
    }


    //获取用户列表
    public enum PageType {

        TWEET(0),
        USER(1);
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
                if (searchUserFragment == null) {
                    searchUserFragment = SearchUserFragment.newInstance(bundle);
                }
                return searchUserFragment;
            } else if (position == PageType.TWEET.getIndex()) {
                if (searchTweetFragment == null) {
                    searchTweetFragment = SearchTweetFragment.newInstance(bundle);
                }
                return searchTweetFragment;
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
