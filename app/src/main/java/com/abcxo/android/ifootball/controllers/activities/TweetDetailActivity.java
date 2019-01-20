package com.abcxo.android.ifootball.controllers.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.detail.TweetDetailFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentTweetMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.MessageFragment;
import com.abcxo.android.ifootball.databinding.ActivityDetailTweetBinding;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetDetailActivity extends CommonActivity {

    private Tweet tweet;
    private long tid;
    private EditText inputET;
    private ViewPager viewPager;

    private TweetDetailFragment tweetDetailFragment;
    private CommentTweetMessageFragment commentTweetMessageFragment;
    private ActivityDetailTweetBinding binding;

    private boolean isComment;

    private Message selectedMessage;


    private BindingHandler handler = new BindingHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            tweet = (Tweet) args.get(Constants.KEY_TWEET);
            tid = args.getLong(Constants.KEY_TID);
            isComment = args.getBoolean(Constants.KEY_IS_COMMENT);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tweet);
        binding.setIsComment(isComment);
        binding.setHandler(handler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(isComment);
        getSupportActionBar().setTitle(R.string.activity_detail_tweet_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setVisibility(isComment ? View.GONE : View.VISIBLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(new TweetDetailNavAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);

        inputET = (EditText) findViewById(R.id.input);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tweet != null &&
                (tweet.uid == UserRestful.INSTANCE.meId() ||
                        (UserRestful.INSTANCE.isLogin() && UserRestful.INSTANCE.me().userType == User.UserType.SPECIAL))) {
            menu.add(R.string.menu_item_tweet_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        return super.onCreateOptionsMenu(menu);
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
        ViewUtils.loading(this);
        TweetRestful.INSTANCE.delete(tweet.id, new TweetRestful.OnTweetRestfulDo() {
            @Override
            public void onSuccess() {
                ViewUtils.dismiss();
                finish();

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

                bundle.putLong(Constants.KEY_TID, getTid());
                commentTweetMessageFragment = CommentTweetMessageFragment.newInstance(bundle);
                commentTweetMessageFragment.setListener(new MessageFragment.Listener() {
                    @Override
                    public void onItemClick(View view, Message message, int position) {
                        if (message.uid != UserRestful.INSTANCE.meId()) {
                            clearText(true, message);
                        }

                    }

                    @Override
                    public void onLoaded(List<Message> messages) {
                        if (tweetDetailFragment != null) {
                            tweetDetailFragment.binding.setHandler(handler);
                            if (messages.size() > 0) {
                                tweetDetailFragment.binding.setMessage0(messages.get(0));
                            }

                            if (messages.size() > 1) {
                                tweetDetailFragment.binding.setMessage1(messages.get(1));
                            }

                            if (messages.size() > 2) {
                                tweetDetailFragment.binding.setMessage2(messages.get(2));
                            }
                        }
                    }
                });
                return commentTweetMessageFragment;


            } else {
                if (position == PageType.DETAIL.getIndex()) {
                    bundle.putParcelable(Constants.KEY_TWEET, tweet);
                    bundle.putLong(Constants.KEY_TID, getTid());
                    tweetDetailFragment = tweetDetailFragment.newInstance(bundle);
                    return tweetDetailFragment;
                } else {
                    bundle.putLong(Constants.KEY_TID, getTid());
                    commentTweetMessageFragment = CommentTweetMessageFragment.newInstance(bundle);
                    commentTweetMessageFragment.setListener(new MessageFragment.Listener() {
                        @Override
                        public void onItemClick(View view, Message message, int position) {
                            if (message.uid != UserRestful.INSTANCE.meId()) {
                                clearText(true, message);
                            }
                        }

                        @Override
                        public void onLoaded(List<Message> messages) {
                            if (tweetDetailFragment != null && tweetDetailFragment.binding != null) {
                                tweetDetailFragment.binding.setHandler(handler);
                                if (messages.size() > 0) {
                                    tweetDetailFragment.binding.setMessage0(messages.get(0));
                                }

                                if (messages.size() > 1) {
                                    tweetDetailFragment.binding.setMessage1(messages.get(1));
                                }

                                if (messages.size() > 2) {
                                    tweetDetailFragment.binding.setMessage2(messages.get(2));
                                }

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

    public long getTid() {
        return tweet != null ? tweet.id : tid;
    }

    public class BindingHandler {

        public void onClickItem(View view) {
            Message message = null;
            View parent = (View) view.getParent();
            if (parent.getId() == R.id.comment_item0) {
                message = commentTweetMessageFragment.adapter.messages.get(0);
            } else if (parent.getId() == R.id.comment_item1) {
                message = commentTweetMessageFragment.adapter.messages.get(1);
            } else if (parent.getId() == R.id.comment_item2) {
                message = commentTweetMessageFragment.adapter.messages.get(2);
            }
            if (message != null && message.uid != UserRestful.INSTANCE.meId()) {
                clearText(true, message);
            }
        }

        public void onClickComment(View view) {
            viewPager.setCurrentItem(PageType.COMMENT.getIndex());
        }

        public void onClickSend(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                String input = inputET.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    ViewUtils.toast(R.string.error_chat);
                } else {
                    long uid2 = selectedMessage != null ? selectedMessage.uid : tweetDetailFragment.getUid();
                    if (uid2 > 0) {
                        Message message = new Message();
                        message.uid = UserRestful.INSTANCE.meId();
                        message.uid2 = uid2;
                        message.tid = getTid();
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
                    } else {
                        ViewUtils.toast(R.string.error_comment);
                    }


                }
            } else {
                NavUtils.toSign(TweetDetailActivity.this);
            }

        }
    }

    private void clearText(boolean open, Message message) {
        selectedMessage = message;
        inputET.setHint(message != null ? "@" + message.title + "：" : getString(R.string.detail_tweet_text_hint));
        inputET.getText().clear();
        if (open) {
            ViewUtils.openKeyboard(this, inputET);
        } else {
            ViewUtils.closeKeyboard(this);
        }

    }


}
