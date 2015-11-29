package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.controllers.activities.AddTeamActivity;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.SignActivity;
import com.abcxo.android.ifootball.controllers.adapters.MainAdapter;
import com.abcxo.android.ifootball.databinding.FragmentMainNavBinding;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.LocationUtils;
import com.abcxo.android.ifootball.utils.NavUtils;

import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.DISCOVER;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.HOME;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.NEWS;
import static com.abcxo.android.ifootball.controllers.adapters.MainAdapter.PageType.TEAM;

public class MainNavFragment extends NavFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private int currentIndex;


    public static MainNavFragment newInstance() {
        return newInstance(null);
    }

    public static MainNavFragment newInstance(Bundle args) {
        MainNavFragment fragment = new MainNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_nav, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMainNavBinding binding = DataBindingUtil.bind(view);
        binding.setHandler(new BindingHandler());
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        viewPager.setOffscreenPageLimit(4);


        viewPager.setAdapter(new MainAdapter(getChildFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final int width = viewPager.getWidth();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();

                if (fab.getVisibility() == View.GONE) {
                    fab.setVisibility(View.VISIBLE);
                }
                if (position == HOME.getIndex()) { // represents transition from page 0 to page 1 (horizontal shift)
                    int translationX = (int) ((-(width - lp.leftMargin - lp.rightMargin - fab.getWidth()) / 2f) * positionOffset);
                    fab.setTranslationX(translationX);
                } else if (position == TEAM.getIndex()) { // represents transition from page 1 to page 2 (vertical shift)
                    fab.setScaleX(1 - positionOffset);
                    fab.setScaleY(1 - positionOffset);

                } else if (position == NEWS.getIndex()) { // represents transition from page 1 to page 2 (vertical shift)
                    fab.setScaleX(positionOffset);
                    fab.setScaleY(positionOffset);
                    int translationX = (int) ((-(width - lp.leftMargin - lp.rightMargin - fab.getWidth()) / 2f) * (1 - positionOffset));
                    fab.setTranslationX(translationX);
                }

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if (fab.getTranslationY() != 0) {
                    fab.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2)).start();
                }
                if (position == NEWS.getIndex()) {
                    fab.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    public class BindingHandler {

        public void onClickFab(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                if (currentIndex == HOME.getIndex()) {
                    startActivity(new Intent(getActivity(), AddTweetActivity.class));
                } else if (currentIndex == TEAM.getIndex()) {
                    startActivity(new Intent(getActivity(), AddTeamActivity.class));
                } else if (currentIndex == NEWS.getIndex()) {
                } else if (currentIndex == DISCOVER.getIndex()) {

                }
            } else {
                NavUtils.toSign(view.getContext());
            }

        }


    }


}
