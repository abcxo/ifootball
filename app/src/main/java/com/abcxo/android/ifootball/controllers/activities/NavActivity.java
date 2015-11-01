package com.abcxo.android.ifootball.controllers.activities;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.nav.FriendFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.NavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.PromptFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.SearchFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MainFragment;
import com.abcxo.android.ifootball.databinding.NavHeaderMainBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.UserRestful;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavFragment.OnFragmentInteractionListener {
    private static final int DELAY_NAV_ITEM = 300;


    private NavFragment mainFg;
    private NavFragment friendFg;
    private NavFragment messageFg;
    private NavFragment promptFg;
    private NavFragment searchFg;

    private Fragment currentFg;

    private int selectedItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toMain();


        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        NavHeaderMainBinding binding = DataBindingUtil.bind(navHeaderView);
        User user = UserRestful.getInstance().getLoginUser();
        binding.setUser(user);

    }


    private void toMain() {
        if (mainFg == null) {
            mainFg = MainFragment.newInstance();

        }
        toNav(mainFg);

    }

    private void toFriend() {
        if (friendFg == null) {
            friendFg = FriendFragment.newInstance();

        }
        toNav(friendFg);

    }

    private void toMessage() {
        if (messageFg == null) {
            messageFg = MessageFragment.newInstance();
        }
        toNav(messageFg);

    }

    private void toPrompt() {
        if (promptFg == null) {
            promptFg = PromptFragment.newInstance();
        }
        toNav(promptFg);


    }

    private void toSearch() {
        if (searchFg == null) {
            searchFg = SearchFragment.newInstance();
        }
        toNav(searchFg);


    }

    private void toNav(Fragment fg) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fg.isAdded()) {
            transaction.add(R.id.content, fg);
        } else {
            transaction.show(fg);
        }
        if (currentFg != null) {
            transaction.hide(currentFg);
        }
        transaction.commit();
        currentFg = fg;


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
