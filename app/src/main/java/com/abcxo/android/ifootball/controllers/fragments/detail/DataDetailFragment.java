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
import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class DataDetailFragment extends Fragment {
    public static DataDetailFragment newInstance() {
        return newInstance(null);
    }

    public static DataDetailFragment newInstance(Bundle args) {
        DataDetailFragment fragment = new DataDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DataDetailAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(8);

        adapter = new DataDetailAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class DataDetailAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public DataDetailAdapter(FragmentManager fm, Context context) {
            super(fm);
            titles = context.getResources().getStringArray(R.array.data_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            String title = titles[position];
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_NAME, title);
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            DataCategoryFragment fragment = DataCategoryFragment.newInstance(bundle);
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
