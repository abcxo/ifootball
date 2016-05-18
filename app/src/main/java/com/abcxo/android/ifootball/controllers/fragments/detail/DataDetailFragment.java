package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.DataDetailActivity;
import com.abcxo.android.ifootball.databinding.FragmentDetailDataBinding;
import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class DataDetailFragment extends Fragment {

    private FragmentDetailDataBinding mDataBinding;

    private String[] titles;

    public static DataDetailFragment newInstance() {
        return newInstance(null);
    }

    public static DataDetailFragment newInstance(Bundle args) {
        DataDetailFragment fragment = new DataDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_data, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBinding.setVariable(BR.handlers, new BindingHandlers());

        titles = getResources().getStringArray(R.array.data_page_list);

    }

    //region ACTION
    public class BindingHandlers {
        public void toPremierLeague(View view) {
            toDataDetailActivity(titles[0]);
        }

        public void toLFP2(View view) {
            toDataDetailActivity(titles[1]);
        }

        public void toUEFA(View view) {
            toDataDetailActivity(titles[2]);
        }

        public void toLiga(View view) {
            toDataDetailActivity(titles[3]);
        }

        public void toSerieA(View view) {
            toDataDetailActivity(titles[4]);
        }

        public void toLFP1(View view) {
            toDataDetailActivity(titles[5]);
        }

        public void toCSL(View view) {
            toDataDetailActivity(titles[6]);
        }

        public void toAFC(View view) {
            toDataDetailActivity(titles[7]);
        }

        private void toDataDetailActivity(String title) {
            Intent intent = new Intent(getActivity(), DataDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_NAME, title);
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    //endregion

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
