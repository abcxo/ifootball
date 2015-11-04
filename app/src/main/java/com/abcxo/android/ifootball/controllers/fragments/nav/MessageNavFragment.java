package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageNavFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageNavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageNavFragment extends NavFragment {


    public static MessageNavFragment newInstance() {
        return newInstance(null);
    }

    public static MessageNavFragment newInstance(Bundle args) {
        MessageNavFragment fragment = new MessageNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_nav, container, false);
    }


}
