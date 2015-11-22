package com.abcxo.android.ifootball.views;

import android.view.animation.Interpolator;

/**
 * Created by shadow on 15/11/22.
 */
public class ReverseInterpolator implements Interpolator {

    private final Interpolator mInterpolator;

    public ReverseInterpolator(Interpolator interpolator){
        mInterpolator = interpolator;
    }

    @Override
    public float getInterpolation(float input) {
        return mInterpolator.getInterpolation(reverseInput(input));
    }

    /**
     * Map value so 0-0.5 = 0-1 and 0.5-1 = 1-0
     */
    private float reverseInput(float input){
        if(input <= 0.5)
            return input*2;
        else
            return Math.abs(input-1)*2;
    }
}
