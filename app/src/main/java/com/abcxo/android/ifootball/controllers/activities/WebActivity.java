package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.detail.WebFragment;

/**
 * Created by shadow on 15/11/4.
 */
public class WebActivity extends AppCompatActivity {
    public WebFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        fragment = WebFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }
    @Override
    public void onBackPressed() {
        if (fragment == null || fragment.onBackPressed() == false) {
            super.onBackPressed();
        }
    }
}
