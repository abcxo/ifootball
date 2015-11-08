package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.abcxo.android.ifootball.utils.ViewUtils;

/**
 * Created by shadow on 15/11/7.
 */
public class FloatingActionButtonScrollBehavior extends FloatingActionButton.Behavior {

    public FloatingActionButtonScrollBehavior(Context context, AttributeSet attrs) {
        super();

    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes));
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);
        if (child.getVisibility() == View.VISIBLE&&child.getScaleX()>0) {
            int offset = ViewUtils.dp2px(child.getContext(), 20);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            if (dyConsumed > offset) {
                child.animate().translationY(child.getHeight() + lp.topMargin + lp.bottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            } else if (dyConsumed < -offset) {
                child.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2)).start();

            }
        }
    }
}
