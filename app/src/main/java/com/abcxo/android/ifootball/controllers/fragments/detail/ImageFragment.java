package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.BindingAdapter;
import com.abcxo.android.ifootball.controllers.fragments.CommonFragment;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by shadow on 15/11/4.
 */
public class ImageFragment extends CommonFragment {

    private List<Image> images = new ArrayList<>();
    private int currentIndex;
    private ViewPager viewPager;

    private Tweet tweet;

    public static ImageFragment newInstance() {
        return newInstance(null);
    }

    public static ImageFragment newInstance(Bundle args) {
        ImageFragment fragment = new ImageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if (args != null) {
            images = args.getParcelableArrayList(Constants.KEY_IMAGES);
            currentIndex = args.getInt(Constants.KEY_IMAGES_INDEX);
            tweet = (Tweet) args.get(Constants.KEY_TWEET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ImageAdapter(images));
        viewPager.setCurrentItem(currentIndex);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Image.ImageType imageType = images.get(0).imageType;
        if (imageType == Image.ImageType.DELETE) {
            menu.add(R.string.menu_item_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        } else if (imageType == Image.ImageType.SAVE_SHARE) {
            menu.add(R.string.menu_item_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            menu.add(R.string.menu_item_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        } else if (imageType == Image.ImageType.TWEET) {
            menu.add(R.string.menu_item_tweet_comment).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            menu.add(R.string.menu_item_tweet_repeat).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            menu.add(R.string.menu_item_tweet_star).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            menu.add(R.string.menu_item_tweet_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            menu.add(R.string.menu_item_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        Image image = images.get(currentIndex);
        if (title.equals(getString(R.string.menu_item_delete))) {
            delete(image);
            return true;
        } else if (title.equals(getString(R.string.menu_item_save))) {
            save(image);
            return true;
        } else if (title.equals(getString(R.string.menu_item_share))) {
            share(image);
            return true;
        } else if (title.equals(getString(R.string.menu_item_tweet_comment))) {
            tweet.getHandler().onClickComment(viewPager);
            return true;
        } else if (title.equals(getString(R.string.menu_item_tweet_repeat))) {
            tweet.getHandler().onClickRepeat(viewPager);
            return true;
        } else if (title.equals(getString(R.string.menu_item_tweet_star))) {
            tweet.getHandler().onClickStar(viewPager);
            return true;
        } else if (title.equals(getString(R.string.menu_item_tweet_share))) {
            tweet.getHandler().onClickShare(viewPager);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void delete(Image image) {

    }

    private void save(Image image) {
        image.getHandler().onClickSave(viewPager);
    }

    private void share(Image image) {
        image.getHandler().onClickShare(viewPager);
    }


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
            View view = View.inflate(getContext(), R.layout.view_image, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.image);
            Image image = images.get(position);
            BindingAdapter.loadImage(photoView, image.url);
            TextView titleTV = (TextView) view.findViewById(R.id.title);
            titleTV.setText(image.title);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
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
}
