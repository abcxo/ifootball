package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abcxo.android.ifootball.R;

/**
 * Created by shadow on 15/11/1.
 */
public class NavFragment extends Fragment {


    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;
    protected boolean isToggle;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        drawer = (DrawerLayout) getNavActivity().findViewById(R.id.drawer_layout);
        syncToolbar();
    }

    private void syncToolbar() {
        getNavActivity().setSupportActionBar(toolbar);
        getNavActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(
                getNavActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            syncToolbar();
        }
    }

    protected AppCompatActivity getNavActivity() {
        return (AppCompatActivity) getActivity();

    }


}
