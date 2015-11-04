package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class SearchNavFragment extends NavFragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_nav, container, false);
    }


}
