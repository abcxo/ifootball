package com.abcxo.android.ifootball.controllers.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.models.Image;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by shadow on 15/11/20.
 */
public class ImageAdapter extends PagerAdapter {

    public List<Image> images;

    public ImageAdapter(List<Image> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Image image = images.get(position);
        BindingAdapter.loadImage(photoView, image.url);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
