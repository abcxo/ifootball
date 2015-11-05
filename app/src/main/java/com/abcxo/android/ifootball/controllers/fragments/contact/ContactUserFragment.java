package com.abcxo.android.ifootball.controllers.fragments.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.sign.UserFragment;

public class ContactUserFragment extends UserFragment {

    public static ContactUserFragment newInstance() {
        return newInstance(null);
    }

    public static ContactUserFragment newInstance(Bundle args) {
        ContactUserFragment fragment = new ContactUserFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

}
