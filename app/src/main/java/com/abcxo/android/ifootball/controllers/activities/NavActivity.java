package com.abcxo.android.ifootball.controllers.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.nav.ContactNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.NavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.SearchNavFragment;
import com.abcxo.android.ifootball.databinding.NavHeaderMainBinding;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.LocationUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.push.PushUtil;

import cn.sharesdk.framework.ShareSDK;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int DELAY_NAV_ITEM = 250;

    private BroadcastReceiver loginReceiver;
    private BroadcastReceiver editReceiver;
    private BroadcastReceiver logoutReceiver;
    private BroadcastReceiver messageClickReceiver;


    private NavFragment mainFg;
    private NavFragment contactFg;
    private NavFragment messageFg;
    private NavFragment searchFg;

    private Fragment currentFg;


    private NavHeaderMainBinding navHeaderMainBinding;

    private int selectedItemId;


    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        init();


        navigationView = (NavigationView) findViewById(R.id.activity_navigationview);
        navigationView.setNavigationItemSelectedListener(this);


//        if (getIntent().getExtras() == null) {
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_item_main));
//        } else {
//            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_item_message));
//        }


        registerBroadcastReceiver();
        //设置Nav页面
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navHeaderMainBinding = DataBindingUtil.bind(navHeaderView);
        User user = UserRestful.INSTANCE.me();
        navHeaderMainBinding.setUser(user);

        if (!Boolean.valueOf(FileUtils.getPreference(Constants.PREFERENCE_FIRST))) {
            startActivity(new Intent(this, WelcomeActivity.class));
            overridePendingTransition(0, 0);
        }

    }


    private void init() {
        ShareSDK.initSDK(this);
        PushUtil.enable(this);
        LocationUtils.saveLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unregisterBroadcastReceiver(loginReceiver);
        Utils.unregisterBroadcastReceiver(editReceiver);
        Utils.unregisterBroadcastReceiver(logoutReceiver);
    }

    private void registerBroadcastReceiver() {
        Utils.registerBroadcastReceiver(loginReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
                navHeaderMainBinding.setUser(user);
                LocationUtils.saveLocation();
                reset();
            }
        }, Constants.ACTION_LOGIN);


        Utils.registerBroadcastReceiver(editReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
                navHeaderMainBinding.setUser(user);
            }
        }, Constants.ACTION_EDIT);

        Utils.registerBroadcastReceiver(logoutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
                navHeaderMainBinding.setUser(user);
                reset();
            }
        }, Constants.ACTION_LOGOUT);


        messageClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Message message = intent.getParcelableExtra("message");
                if (message.messageType == Message.MessageType.CHAT) {
                    if (!ChatDetailActivity.isChatting(message.uid, message.uid2)) {
                        NavUtils.toChatDetail(NavActivity.this, message.uid2, message.uid);
                    } else {

                    }
                } else {
                    Intent i = new Intent(NavActivity.this, NavActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_item_message));
                }


            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(messageClickReceiver, new IntentFilter(Constants.ACTION_MESSAGE_CLICK));
    }


    private void reset() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (mainFg != null) {
            fragmentManager.beginTransaction().remove(mainFg).commitAllowingStateLoss();
            mainFg = null;
        }
        if (contactFg != null) {
            fragmentManager.beginTransaction().remove(contactFg).commitAllowingStateLoss();
            contactFg = null;
        }
        if (messageFg != null) {
            fragmentManager.beginTransaction().remove(messageFg).commitAllowingStateLoss();
            messageFg = null;
        }
        if (searchFg != null) {
            fragmentManager.beginTransaction().remove(searchFg).commitAllowingStateLoss();
            searchFg = null;
        }

        selectedItemId = 0;
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_item_main));
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
                item.setChecked(true);
                if (id == R.id.nav_item_main) {
                    toMain();
                } else if (id == R.id.nav_item_contact) {
                    toContact();
                } else if (id == R.id.nav_item_message) {
                    toMessage();
                } else if (id == R.id.nav_item_search) {
                    toSearch();
                }
            }

        } else if (groupId == R.id.nav_group_append) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (id == R.id.nav_item_setting) {
                        UserRestful.INSTANCE.logout();
//                        toAppend(SettingActivity.class);
                    } else if (id == R.id.nav_item_help) {
                        toAppend(WelcomeActivity.class);
                    } else if (id == R.id.nav_item_about) {
                        toAppend(AboutActivity.class);
                    }

                }
            }, DELAY_NAV_ITEM);


        }

        return true;
    }


    private void toMain() {
        if (mainFg == null) {
            mainFg = MainNavFragment.newInstance();
        }
        toNav(mainFg);

    }

    private void toContact() {
        if (contactFg == null) {
            contactFg = ContactNavFragment.newInstance();

        }
        toNav(contactFg);

    }

    private void toMessage() {
        if (messageFg == null) {
            messageFg = MessageNavFragment.newInstance();
        }
        toNav(messageFg);

    }


    private void toSearch() {
        if (searchFg == null) {
            searchFg = SearchNavFragment.newInstance();
        }
        toNav(searchFg);


    }

    private void toNav(Fragment fg) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fg.isAdded()) {
            transaction.add(R.id.content, fg);
        } else {
            transaction.show(fg);
        }
        if (currentFg != null) {
            transaction.hide(currentFg);
        }
        transaction.commitAllowingStateLoss();
        currentFg = fg;


    }

    private void toAppend(Class<? extends Activity> to) {
        Intent intent = new Intent();
        intent.setClass(this, to);
        startActivity(intent);

    }


}
