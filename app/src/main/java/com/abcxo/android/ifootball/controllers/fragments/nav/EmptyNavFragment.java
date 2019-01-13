package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class EmptyNavFragment extends NavFragment{
    public static EmptyNavFragment newInstance() {
        return newInstance(null);
    }

    public static EmptyNavFragment newInstance(Bundle args) {
        EmptyNavFragment fragment = new EmptyNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_nav, container, false);
    }
}
