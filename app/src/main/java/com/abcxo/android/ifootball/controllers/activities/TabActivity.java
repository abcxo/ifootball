package com.abcxo.android.ifootball.controllers.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.SearchNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.TabNavFragment;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.LocationUtils;
import com.abcxo.android.ifootball.utils.LogUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by shadow on 15/11/4.
 */
public class TabActivity extends CommonActivity {

    private BroadcastReceiver loginReceiver;
    private BroadcastReceiver editReceiver;
    private BroadcastReceiver logoutReceiver;
    private BroadcastReceiver messageReceiver;
    private BroadcastReceiver messageClickReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastReceiver();
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_tab);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.KEY_IS_SELECT, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, TabNavFragment.newInstance(bundle)).commit();
        init();
    }

    private void init() {
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                LogUtils.d(" onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        ShareSDK.initSDK(this);
        LocationUtils.saveLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unregisterBroadcastReceiver(loginReceiver);
        Utils.unregisterBroadcastReceiver(editReceiver);
        Utils.unregisterBroadcastReceiver(logoutReceiver);
        Utils.unregisterBroadcastReceiver(messageReceiver);
        Utils.unregisterBroadcastReceiver(messageClickReceiver);
    }

    private void registerBroadcastReceiver() {
        Utils.registerBroadcastReceiver(loginReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
                LocationUtils.saveLocation();
                recreate();
            }
        }, Constants.ACTION_LOGIN);


        Utils.registerBroadcastReceiver(editReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
            }
        }, Constants.ACTION_EDIT);

        Utils.registerBroadcastReceiver(logoutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                User user = UserRestful.INSTANCE.me();
                recreate();
            }
        }, Constants.ACTION_LOGOUT);


        messageClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context contextcontext, Intent intent) {
                Message message = intent.getParcelableExtra("message");
                if (message.messageType == Message.MessageType.CHAT) {
                    if (!ChatDetailActivity.isChatting(message.uid, message.uid2)) {
                        NavUtils.toChatDetail(TabActivity.this, message.uid2, message.uid);
                    } else {

                    }
                } else {
                    Intent i = new Intent(TabActivity.this, MessageActivity.class);
                    startActivity(i);
                }


            }
        };

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context contextcontext, Intent intent) {
                Message message = intent.getParcelableExtra("message");
            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(messageClickReceiver, new IntentFilter(Constants.ACTION_MESSAGE_CLICK));
        localBroadcastManager.registerReceiver(messageReceiver, new IntentFilter(Constants.ACTION_MESSAGE));
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((SystemClock.elapsedRealtime() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = SystemClock.elapsedRealtime();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
