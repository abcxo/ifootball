package com.abcxo.android.ifootball.controllers.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.detail.UserImageFragment;
import com.abcxo.android.ifootball.controllers.fragments.detail.UserTweetFragment;

import static com.abcxo.android.ifootball.controllers.adapters.UserDetailAdapter.PageType.IMAGE;
import static com.abcxo.android.ifootball.controllers.adapters.UserDetailAdapter.PageType.TWEET;

/**
 * Created by shadow on 15/11/1.
 */
public class UserDetailAdapter extends FragmentPagerAdapter {

    private long uid;

    //获取用户列表
    public enum PageType {
        TWEET(0),
        IMAGE(1);
        private int index;

        PageType(int index) {
            this.index = index;
        }

        public static int size() {
            return PageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    private String[] titles;

    public UserDetailAdapter(FragmentManager fm, Context context, long uid) {
        super(fm);
        this.uid = uid;
        titles = context.getResources().getStringArray(R.array.user_detail_page_list);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID,uid);
        if (position == TWEET.getIndex()) {
            return UserTweetFragment.newInstance(bundle);
        } else if (position == IMAGE.getIndex()) {
            return UserImageFragment.newInstance(bundle);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }


}
