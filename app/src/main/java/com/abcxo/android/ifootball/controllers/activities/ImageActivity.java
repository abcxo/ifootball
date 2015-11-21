package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.detail.ImageFragment;

/**
 * Created by shadow on 15/11/20.
 */
public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ImageFragment.newInstance(getIntent().getExtras())).commit();
    }
}
