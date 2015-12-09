package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.utils.FileUtils;

/**
 * Created by shadow on 15/11/4.
 */
public class WelcomeFragment extends Fragment {

    private ViewPager viewPager;

    public static WelcomeFragment newInstance() {
        return newInstance(null);
    }

    public static WelcomeFragment newInstance(Bundle args) {
        WelcomeFragment fragment = new WelcomeFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new WelcomeAdapter());


    }

    public class WelcomeAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getContext(), R.layout.view_welcome, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Button button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileUtils.setPreference(Constants.PREFERENCE_FIRST, String.valueOf(true));
                    getActivity().finish();
                }
            });
            if (position == 0) {
                imageView.setImageResource(R.drawable.img_welcome0);
            } else if (position == 1) {
                imageView.setImageResource(R.drawable.img_welcome1);
            } else {
                imageView.setImageResource(R.drawable.img_welcome2);
            }
            container.addView(view);
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
