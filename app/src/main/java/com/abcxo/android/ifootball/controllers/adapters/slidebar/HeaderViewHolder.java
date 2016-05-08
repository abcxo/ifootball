package com.abcxo.android.ifootball.controllers.adapters.slidebar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcxo.android.ifootball.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

public class HeaderViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private final ImageView iv_arrow;
    private TextView tv_header;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        tv_header = (TextView) itemView.findViewById(R.id.tv_header);

        iv_arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
    }

    public void bind(SlidebarHeader headerModel) {
        tv_header.setText(headerModel.getmTitle());
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                iv_arrow.setRotation(ROTATED_POSITION);
            } else {
                iv_arrow.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                 rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            iv_arrow.startAnimation(rotateAnimation);
        }
    }
}
