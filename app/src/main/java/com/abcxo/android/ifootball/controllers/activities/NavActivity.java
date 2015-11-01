package com.abcxo.android.ifootball.controllers.activities;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.nav.FriendFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.NavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.PromptFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.SearchFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavFragment.OnFragmentInteractionListener {
    private static final int DELAY_NAV_ITEM = 300;


    private NavFragment mainFg;
    private NavFragment friendFg;
    private NavFragment messageFg;
    private NavFragment promptFg;
    private NavFragment searchFg;

    private int selectedItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        toMain();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void toMain() {
        if (mainFg == null) mainFg = MainFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content, mainFg).commit();
    }

    private void toFriend() {
        if (friendFg == null) friendFg = FriendFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content, friendFg).commit();
    }

    private void toMessage() {
        if (messageFg == null) messageFg = MessageFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content, messageFg).commit();
    }

    private void toPrompt() {
        if (promptFg == null) promptFg = PromptFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content, promptFg).commit();
    }

    private void toSearch() {
        if (searchFg == null) searchFg = SearchFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content, searchFg).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        final int groupId = item.getGroupId();
        final int id = item.getItemId();

        if (groupId == R.id.nav_group_nav) {
            if (selectedItemId != id) {
                selectedItemId = id;
                if (id == R.id.nav_item_main) {
                    toMain();
                } else if (id == R.id.nav_item_friend) {
                    toFriend();
                } else if (id == R.id.nav_item_message) {
                    toMessage();
                } else if (id == R.id.nav_item_prompt) {
                    toPrompt();
                } else if (id == R.id.nav_item_search) {
                    toSearch();
                }
            }

        } else if (groupId == R.id.nav_group_append) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (id == R.id.nav_item_setting) {
                        goAppend(SettingActivity.class);
                    } else if (id == R.id.nav_item_help) {
                        goAppend(HelpActivity.class);
                    } else if (id == R.id.nav_item_about) {
                        goAppend(AboutActivity.class);
                    }

                }
            }, DELAY_NAV_ITEM);


        }


        return true;
    }

    private void goAppend(Class<? extends Activity> to) {
        Intent intent = new Intent();
        intent.setClass(this, to);
        startActivity(intent);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
