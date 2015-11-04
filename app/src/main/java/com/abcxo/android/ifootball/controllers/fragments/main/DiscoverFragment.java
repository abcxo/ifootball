package com.abcxo.android.ifootball.controllers.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class DiscoverFragment extends Fragment {
    public static DiscoverFragment newInstance() {
        return newInstance(null);
    }

    public static DiscoverFragment newInstance(Bundle args) {
        DiscoverFragment fragment = new DiscoverFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }
}
