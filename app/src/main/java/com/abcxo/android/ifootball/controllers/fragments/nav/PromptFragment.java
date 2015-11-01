package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

public class PromptFragment extends NavFragment {

    public static PromptFragment newInstance() {
        return newInstance(null);
    }

    public static PromptFragment newInstance(Bundle args) {
        PromptFragment fragment = new PromptFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prompt, container, false);
    }

}
