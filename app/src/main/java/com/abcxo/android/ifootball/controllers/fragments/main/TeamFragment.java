package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class TeamFragment extends TweetFragment {

    public static TeamFragment newInstance() {
        return newInstance(null);
    }

    public static TeamFragment newInstance(Bundle args) {
        TeamFragment fragment = new TeamFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

}
