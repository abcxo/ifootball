package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by shadow on 15/11/4.
 */
public class DataCategoryFragment extends Fragment {
    protected String name;
    protected long uid;

    public static DataCategoryFragment newInstance() {
        return newInstance(null);
    }

    public static DataCategoryFragment newInstance(Bundle args) {
        DataCategoryFragment fragment = new DataCategoryFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DataCategoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString(Constants.KEY_NAME);
            uid = args.getLong(Constants.KEY_UID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        adapter = new DataCategoryAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class DataCategoryAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public DataCategoryAdapter(FragmentManager fm, Context context) {
            super(fm);
            titles = context.getResources().getStringArray(R.array.data_category_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            String title = titles[position];
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_NAME, name);
            bundle.putString(Constants.KEY_CATEGORY, title);
            bundle.putLong(Constants.KEY_UID, uid);
            DataFragment fragment = DataFragment.newInstance(bundle);
            return fragment;
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
