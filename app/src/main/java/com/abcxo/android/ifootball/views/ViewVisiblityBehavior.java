package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by shadow on 15/11/21.
 */
public class ViewVisiblityBehavior extends CoordinatorLayout.Behavior<View> {


    private float maxTop;

    public ViewVisiblityBehavior(Context context, AttributeSet attrs) {
        super();

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                          View dependency) {
        if (dependency instanceof AppBarLayout) {
            if (maxTop == 0) maxTop = child.getTop();
            float alpha = child.getTop() / maxTop;
            child.setAlpha(alpha);
        }
        return false;
    }
}