package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.AddTeamActivity;
import com.abcxo.android.ifootball.controllers.activities.AddTweetActivity;
import com.abcxo.android.ifootball.controllers.activities.NavActivity;
import com.abcxo.android.ifootball.controllers.fragments.detail.DataDetailFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.HomeTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.NewsTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.TeamTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.main.VideoTweetFragment;
import com.abcxo.android.ifootball.databinding.FragmentMainNavBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.LocationUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.IconFontView;

import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.DATA;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.HOME;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.NEWS;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.TEAM;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MainNavFragment.PageType.VIDEO;

public class MainNavFragment extends NavFragment {

    private FragmentMainNavBinding mDataBinding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private IconFontView ifv_write_twitter;
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
        mDataBinding = DataBindingUtil.bind(view);
        mDataBinding.setHandler(new BindingHandler());
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        ifv_write_twitter = (IconFontView) view.findViewById(R.id.ifv_write_twitter);

        viewPager.setOffscreenPageLimit(5);

        MainAdapter adapter = new MainAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));  // 设置tab图片和文字
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final int width = viewPager.getWidth();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();

                if (position == HOME.getIndex()) { // represents transition from page 0 to page 1 (horizontal shift)
                    setFabBackground(R.string.iconfont_write_twitter);
                    fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(4)).start();
                } else if (position == TEAM.getIndex()) { // represents transition from page 1 to page 2 (vertical shift)
                    setFabBackground(R.string.iconfont_add_team);
                    fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(4)).start();
                }
//                else if (position == LIVE.getIndex()) { // represents transition from page 1 to page 2 (vertical shift)
//                }
                else if (position == VIDEO.getIndex() ||  position == NEWS.getIndex() || position == DATA.getIndex()) { // represents transition from page 1 to page 2 (vertical shift)
                    fab.animate().translationY(fab.getHeight() + ViewUtils.dp2px(16)).setInterpolator(new AccelerateInterpolator(4)).start();
                }

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if (position == DATA.getIndex()) {
                    LocationUtils.saveLocation();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mDataBinding.rltSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavActivity) getActivity()).toSearch();
            }
        });

        mDataBinding.ifvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavActivity) getActivity()).toMessage();
            }
        });

        mDataBinding.ifvPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.END);
            }
        });

        ifv_write_twitter.post(new Runnable() {
            @Override
            public void run() {
                setFabBackground(R.string.iconfont_write_twitter);
            }
        });
    }

    private void setFabBackground(int stringResID) {
        ifv_write_twitter.setText(stringResID);
        ifv_write_twitter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        ifv_write_twitter.setDrawingCacheEnabled(true);

        ifv_write_twitter.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        ifv_write_twitter.layout(0, 0, ifv_write_twitter.getMeasuredWidth(), ifv_write_twitter.getMeasuredHeight());

        ifv_write_twitter.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(ifv_write_twitter.getDrawingCache());
        fab.setImageBitmap(b);
        ifv_write_twitter.setDrawingCacheEnabled(false); // clear drawing cache
    }


    //获取用户列表
    public enum PageType {

        HOME(0),
        TEAM(1),
//        LIVE(2),
        VIDEO(2),
        NEWS(3),
        DATA(4);
        private int index;

        PageType(int index) {
            this.index = index;
        }

        public static int size() {
            return PageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    public class MainAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MainAdapter(FragmentManager fm, Context context) {
            super(fm);
            titles = context.getResources().getStringArray(R.array.main_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());

            if (!hasNews() && position == PageType.NEWS.getIndex()) {
                position++;
            }

            if (position == HOME.getIndex()) {
                return HomeTweetFragment.newInstance(bundle);
            } else if (position == TEAM.getIndex()) {
                return TeamTweetFragment.newInstance(bundle);
            }
//            else if (position == LIVE.getIndex()) {
//                return LiveFragment.newInstance(bundle);
//            }
            else if (position == NEWS.getIndex()) {
                return NewsTweetFragment.newInstance(bundle);
            } else if (position == VIDEO.getIndex()) {
                return VideoTweetFragment.newInstance(bundle);
            } else if (position == DATA.getIndex()) {
//                return DiscoverUserFragment.newInstance(bundle);
                return DataDetailFragment.newInstance(bundle);
            }
            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (!hasNews() && position == PageType.NEWS.getIndex()) {
                return titles[position + 1];
            }
            return titles[position];


        }

        @Override
        public int getCount() {
            return hasNews() ? titles.length : titles.length - 1;
        }

        public boolean hasNews() {
            return UserRestful.INSTANCE.isLogin() && UserRestful.INSTANCE.me().userType == User.UserType.SPECIAL;
        }

        public View getTabView(int position) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.tab_main, null);
            TextView tv_tab = (TextView) tab.findViewById(R.id.tv_tab);
            if (!hasNews() && position == PageType.NEWS.getIndex()) {
                tv_tab.setText(titles[position + 1]);
            } else {
                tv_tab.setText(titles[position]);
            }

            IconFontView ifv_tab = (IconFontView) tab.findViewById(R.id.ifv_tab);

            switch (position) {
                case 0:
                    ifv_tab.setText(R.string.iconfont_follow);
                    break;
                case 1:
                    ifv_tab.setText(R.string.iconfont_team);
                    break;
                case 2:
                    ifv_tab.setText(R.string.iconfont_video);
                    break;
                case 3:
                    if (hasNews()) {
                        ifv_tab.setText(R.string.iconfont_news);
                    } else {
                        ifv_tab.setText(R.string.iconfont_data);
                    }
                    break;
                case 4:
                    ifv_tab.setText(R.string.iconfont_data);
                    break;
                case 5:
                    ifv_tab.setText(R.string.iconfont_data);
                    break;
            }
            return tab;
        }
    }

    public class BindingHandler {

        public void onClickFab(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                if (currentIndex == HOME.getIndex()) {
                    startActivity(new Intent(getActivity(), AddTweetActivity.class));
                } else if (currentIndex == TEAM.getIndex()
//                        || currentIndex == LIVE.getIndex()
                        ) {
                    startActivity(new Intent(getActivity(), AddTeamActivity.class));
                } else if (currentIndex == NEWS.getIndex()) {
                } else if (currentIndex == DATA.getIndex()) {

                }
            } else {
                NavUtils.toSign(view.getContext());
            }

        }


        public void onClickSearch(View view) {
            NavUtils.toSearch(view.getContext());
        }

        public void onClickMessage(View view) {
            NavUtils.toMessage(view.getContext());
        }
    }
}
