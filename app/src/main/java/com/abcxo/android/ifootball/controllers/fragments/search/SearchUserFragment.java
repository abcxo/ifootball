package com.abcxo.android.ifootball.controllers.fragments.search;

import android.os.Bundle;

import com.abcxo.android.ifootball.controllers.fragments.sign.UserFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class SearchUserFragment extends UserFragment {
    public static SearchUserFragment newInstance() {
        return newInstance(null);
    }

    public String keyword="";

    public static SearchUserFragment newInstance(Bundle args) {
        SearchUserFragment fragment = new SearchUserFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public boolean needFirstRefresh() {
        return false;
    }

    @Override
    protected UserRestful.GetsType getGetsType() {
        return UserRestful.GetsType.SEARCH;
    }
}
