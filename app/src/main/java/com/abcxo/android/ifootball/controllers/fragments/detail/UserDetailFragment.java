package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.CommonFragment;
import com.abcxo.android.ifootball.databinding.FragmentDetailUserBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.IconFontView;
import com.abcxo.android.ifootball.views.SelectIconFontView;


/**
 * Created by shadow on 15/11/4.
 */
public class UserDetailFragment extends CommonFragment {
    private User user;
    private long uid;
    private String name;
    private FragmentDetailUserBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static UserDetailFragment newInstance() {
        return newInstance(null);
    }

    public static UserDetailFragment newInstance(Bundle args) {
        UserDetailFragment fragment = new UserDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            user = (User) args.get(Constants.KEY_USER);
            uid = args.getLong(Constants.KEY_UID);
            name = args.getString(Constants.KEY_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_user, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
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


        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);


        if (user != null) {
            bindData();
        } else {
            if (uid == UserRestful.INSTANCE.meId()) {
                user = UserRestful.INSTANCE.me();
                bindData();
            } else {
                ViewUtils.loading(getActivity());
                UserRestful.OnUserRestfulGet onGet = new UserRestful.OnUserRestfulGet() {
                    @Override
                    public void onSuccess(User user) {
                        UserDetailFragment.this.user = user;
                        bindData();
                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.toast(error.msg);
                    }

                    @Override
                    public void onFinish() {
                        ViewUtils.dismiss();
                    }
                };
                if (uid > 0) {
                    UserRestful.INSTANCE.get(uid, onGet);
                } else {
                    UserRestful.INSTANCE.get(name, onGet);
                }
            }

        }

    }

    public void bindData() {
        binding.setUser(user);
        UserDetailAdapter adapter = new UserDetailAdapter(getChildFragmentManager(), getActivity(), user != null ? user.id : uid);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));  // 设置tab图片和文字
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }


    //获取用户列表
    public enum PageType {
        TWEET(0),
        IMAGE(1);
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

    public class UserDetailAdapter extends FragmentPagerAdapter {

        private long uid;


        private String[] titles;

        public UserDetailAdapter(FragmentManager fm, Context context, long uid) {
            super(fm);
            this.uid = uid;
            titles = context.getResources().getStringArray(R.array.user_detail_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, uid);
            if (position == PageType.TWEET.getIndex()) {
                return UserTweetFragment.newInstance(bundle);
            } else if (position == PageType.IMAGE.getIndex()) {
                return UserImageFragment.newInstance(bundle);

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        public View getTabView(int position) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_tab_nav, null);
            IconFontView ifv_tab = (IconFontView) tab.findViewById(R.id.ifv_tab);
            switch (position) {
                case 0:
                    ifv_tab.setText(R.string.iconfont_user_tweet);
                    break;
                case 1:
                    ifv_tab.setText(R.string.iconfont_user_image);
                    break;
            }
            return tab;
        }

    }


}
