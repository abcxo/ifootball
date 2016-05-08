package com.abcxo.android.ifootball.controllers.adapters.slidebar;

import com.abcxo.android.ifootball.models.User;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class SlidebarHeader implements ParentListItem {

    private String mTitle;
    private List<User> mUsers;

    public SlidebarHeader(String name, List<User> users) {
        mTitle = name;
        mUsers = users;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmUsers(List<User> mUsers) {
        mUsers.clear();
        mUsers.addAll(mUsers);
//        this.mUsers = mUsers;
    }

    @Override
    public List<?> getChildItemList() {
        return mUsers;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}
