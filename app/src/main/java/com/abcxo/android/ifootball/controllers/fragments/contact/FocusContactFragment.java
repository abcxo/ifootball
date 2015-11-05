package com.abcxo.android.ifootball.controllers.fragments.contact;

import android.os.Bundle;

/**
 * Created by shadow on 15/11/5.
 */
public class FocusContactFragment extends ContactUserFragment {
    public static FocusContactFragment newInstance() {
        return newInstance(null);
    }

    public static FocusContactFragment newInstance(Bundle args) {
        FocusContactFragment fragment = new FocusContactFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }
}
