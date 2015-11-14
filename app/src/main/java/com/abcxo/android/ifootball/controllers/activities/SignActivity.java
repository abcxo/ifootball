package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.sign.LoginSignFragment;

/**
 * Created by shadow on 15/11/4.
 */
public class SignActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, LoginSignFragment.newInstance()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.content, CompleteSignFragment.newInstance()).commit();
    }

}
