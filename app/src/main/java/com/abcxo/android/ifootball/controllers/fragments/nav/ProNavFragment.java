package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.main.HomeTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.ProTweetFragment;

public class ProNavFragment extends NavFragment {
    public static ProNavFragment newInstance() {
        return newInstance(null);
    }

    public static ProNavFragment newInstance(Bundle args) {
        ProNavFragment fragment = new ProNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pro_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNavActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
        getNavActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, ProTweetFragment.newInstance(getArguments()))
                .commit();
    }


}
