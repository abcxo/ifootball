package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.nav.ContactNavFragment;

/**
 * Created by shadow on 15/11/4.
 */
public class ContactActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.KEY_IS_SELECT, getIntent().getBooleanExtra(Constants.KEY_IS_SELECT,false));
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ContactNavFragment.newInstance(bundle)).commit();
    }

}
