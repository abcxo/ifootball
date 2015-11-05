package com.abcxo.android.ifootball.controllers.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.sign.UserFragment;

public class DiscoverUserFragment extends UserFragment {
    public static DiscoverUserFragment newInstance() {
        return newInstance(null);
    }

    public static DiscoverUserFragment newInstance(Bundle args) {
        DiscoverUserFragment fragment = new DiscoverUserFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


}
