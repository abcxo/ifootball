package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

/**
 * Created by shadow on 15/11/4.
 */
public class UserDetailFragment extends Fragment{
    public static UserDetailFragment newInstance() {
        return newInstance(null);
    }

    public static UserDetailFragment newInstance(Bundle args) {
        UserDetailFragment fragment = new UserDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
