package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class SearchFragment extends NavFragment {

    public static SearchFragment newInstance() {
        return newInstance(null);
    }

    public static SearchFragment newInstance(Bundle args) {
        SearchFragment fragment = new SearchFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public int getToolbarResId() {
        return R.id.search_toolbar;
    }

}
