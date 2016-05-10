package com.abcxo.android.ifootball.controllers.adapters.slidebar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.models.User;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class SidebarAdapter extends ExpandableRecyclerAdapter<HeaderViewHolder, ChildrenViewHolder> {

    private LayoutInflater mInflater;

    public SidebarAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HeaderViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View recipeView = mInflater.inflate(R.layout.item_slidebar_header, parentViewGroup, false);
        return new HeaderViewHolder(recipeView);
    }

    @Override
    public ChildrenViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View ingredientView = mInflater.inflate(R.layout.item_slidebar_children, childViewGroup, false);
        return new ChildrenViewHolder(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(HeaderViewHolder headerViewHolder, int position, ParentListItem parentListItem) {
        SlidebarHeader headerModel = (SlidebarHeader) parentListItem;
        headerViewHolder.bind(headerModel);
    }

    @Override
    public void onBindChildViewHolder(ChildrenViewHolder childrenViewHolder, int position, Object childListItem) {
        User user = (User) childListItem;
        childrenViewHolder.bind(user);
    }
}
