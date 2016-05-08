package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.main.DiscoverUserFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;

/**
 * Created by shadow on 15/11/4.
 */
public class NearbyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nearby);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, DiscoverUserFragment.newInstance(bundle)).commit();
    }
}
