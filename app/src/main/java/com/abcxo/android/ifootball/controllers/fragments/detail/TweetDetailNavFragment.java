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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentTweetMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.MessageFragment;
import com.abcxo.android.ifootball.databinding.FragmentDetailTweetNavBinding;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetDetailNavFragment extends DetailFragment {

    private Tweet tweet;
    private long tid;
    private EditText inputET;
    private ViewPager viewPager;

    private TweetDetailFragment tweetDetailFragment;
    private CommentTweetMessageFragment commentTweetMessageFragment;
    private FragmentDetailTweetNavBinding binding;

    private boolean isComment;

    private Message selectedMessage;

    public static TweetDetailNavFragment newInstance() {
        return newInstance(null);
    }

    public static TweetDetailNavFragment newInstance(Bundle args) {
        TweetDetailNavFragment fragment = new TweetDetailNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tweet = (Tweet) args.get(Constants.KEY_TWEET);
            tid = args.getLong(Constants.KEY_TID);
            isComment = args.getBoolean(Constants.KEY_IS_COMMENT);
            if (tweet != null && tweet.uid == UserRestful.INSTANCE.meId()) {
                setHasOptionsMenu(true);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(R.string.menu_item_tweet_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals(getString(R.string.menu_item_tweet_delete))) {
            deleteTweet();
        }
        return true;
    }


    public void deleteTweet() {
        ViewUtils.loading(getActivity());
        TweetRestful.INSTANCE.delete(tweet.id, new TweetRestful.OnTweetRestfulDo() {
            @Override
            public void onSuccess() {
                ViewUtils.dismiss();
                getActivity().finish();

            }

            @Override
            public void onError(RestfulError error) {
                ViewUtils.toast(error.msg);
                ViewUtils.dismiss();
            }

            @Override
            public void onFinish() {

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tweet_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        binding.setHandler(new BindingHandler());

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


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(new TweetDetailNavAdapter(getChildFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewPager);

        inputET = (EditText) view.findViewById(R.id.input);

    }

    //获取用户列表
    public enum PageType {

        DETAIL(0),
        COMMENT(1);
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

    public class TweetDetailNavAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public TweetDetailNavAdapter(FragmentManager fm, Context context) {
            super(fm);
            if (isComment) {
                titles = context.getResources().getStringArray(R.array.tweet_comment_page_list);

            } else {
                titles = context.getResources().getStringArray(R.array.tweet_detail_page_list);
            }

        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            if (isComment) {

                bundle.putLong(Constants.KEY_TID, tweet != null ? tweet.id : tid);
                commentTweetMessageFragment = CommentTweetMessageFragment.newInstance(bundle);
                commentTweetMessageFragment.setListener(new MessageFragment.Listener() {
                    @Override
                    public void onItemClick(View view, Message message, int position) {
                        if (message.uid != UserRestful.INSTANCE.meId()) {
                            clearText(true, message);
                        }
                    }
                });
                return commentTweetMessageFragment;


            } else {
                if (position == PageType.DETAIL.getIndex()) {
                    bundle.putParcelable(Constants.KEY_TWEET, tweet);
                    bundle.putLong(Constants.KEY_TID, tid);
                    tweetDetailFragment = tweetDetailFragment.newInstance(bundle);
                    return tweetDetailFragment;
                } else {
                    bundle.putLong(Constants.KEY_TID, tweet != null ? tweet.id : tid);
                    commentTweetMessageFragment = CommentTweetMessageFragment.newInstance(bundle);
                    commentTweetMessageFragment.setListener(new MessageFragment.Listener() {
                        @Override
                        public void onItemClick(View view, Message message, int position) {
                            if (message.uid != UserRestful.INSTANCE.meId()) {
                                clearText(true, message);
                            }
                        }
                    });
                    return commentTweetMessageFragment;
                }
            }


        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }


    }

    public class BindingHandler {
        public void onClickComment(View view) {
            clearText(true, null);
        }

        public void onClickSend(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                String input = inputET.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    ViewUtils.toast(R.string.error_chat);
                } else {
                    Message message = new Message();
                    message.uid = UserRestful.INSTANCE.meId();
                    message.uid2 = selectedMessage != null ? selectedMessage.uid : tweet.uid;
                    message.tid = tweet.id;
                    message.title = UserRestful.INSTANCE.me().name;
                    message.icon = UserRestful.INSTANCE.me().avatar;
                    message.content = selectedMessage != null ? "回复@" + selectedMessage.title + "：" + input : input;
                    message.messageType = Message.MessageType.COMMENT;
                    message.mainType = Message.MessageMainType.COMMENT_TWEET;
                    message.detailType = Message.MessageDetailType.COMMENT;
                    message.time = Utils.time();
                    commentTweetMessageFragment.insertMessage(message);

                    clearText(false, null);
                    TweetRestful.INSTANCE.comment(message, new TweetRestful.OnTweetRestfulDo() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(RestfulError error) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    });
                    viewPager.setCurrentItem(PageType.COMMENT.getIndex());
                }
            } else {
                NavUtils.toSign(getActivity());
            }

        }
    }

    private void clearText(boolean open, Message message) {
        selectedMessage = message;
        inputET.setHint(message != null ? "@" + message.title + "：" : getString(R.string.detail_tweet_text_hint));
        inputET.getText().clear();
        if (open) {
            ViewUtils.openKeyboard(getActivity(), inputET);
        } else {
            ViewUtils.closeKeyboard(getActivity());
        }

    }
}
