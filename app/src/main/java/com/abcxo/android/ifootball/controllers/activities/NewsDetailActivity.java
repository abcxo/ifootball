package com.abcxo.android.ifootball.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.detail.NewsDetailFragment;

/**
 * Created by shadow on 15/11/4.
 */
public class NewsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_news);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, NewsDetailFragment.newInstance(getIntent().getExtras())).commit();
    }


}
