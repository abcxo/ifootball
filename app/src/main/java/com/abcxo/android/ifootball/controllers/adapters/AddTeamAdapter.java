package com.abcxo.android.ifootball.controllers.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTeamFragment;
import com.abcxo.android.ifootball.controllers.fragments.add.TeamFragment;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.search.SearchUserFragment;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.UserRestful;

import java.util.List;

import static com.abcxo.android.ifootball.controllers.adapters.SearchAdapter.PageType.TWEET;
import static com.abcxo.android.ifootball.controllers.adapters.SearchAdapter.PageType.USER;

/**
 * Created by shadow on 15/11/1.
 */
public class AddTeamAdapter extends FragmentPagerAdapter {

    private String[] titles;

    private AddTeamFragment.BindingHandler handler;

    public AddTeamAdapter(FragmentManager fm, Context context, AddTeamFragment.BindingHandler handler) {
        super(fm);
        this.handler = handler;
        titles = context.getResources().getStringArray(R.array.team_page_list);
    }

    @Override
    public Fragment getItem(int position) {
        String title = titles[position];
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_NAME, title);
        TeamFragment fragment = TeamFragment.newInstance(bundle);
        fragment.setListener(new TeamFragment.Listener() {
            @Override
            public void onLoaded(List<User> users) {
                handler.onLoaded(users);
            }

            @Override
            public void onItemClick(View view, User user, int position) {
                handler.onClickItem(view);
            }
        });
        return fragment;
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
