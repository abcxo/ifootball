package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.nav.DiscoverNavFragment;
import com.abcxo.android.ifootball.controllers.fragments.nav.SettingFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class DiscoverActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, DiscoverNavFragment.newInstance(bundle)).commit();
    }

}
