package com.abcxo.android.ifootball.controllers.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView commentTV;
    private TextView titleTV;

    private TweetDetailFragment tweetDetailFragment;
    private CommentTweetMessageFragment commentTweetMessageFragment;
    private ActivityDetailTweetBinding binding;

    private boolean isComment;

    private Message selectedMessage;


    private BindingHandler handler = new BindingHandler();

    private int commentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            tweet = (Tweet) args.get(Constants.KEY_TWEET);
            tid = args.getLong(Constants.KEY_TID);
            isComment = args.getBoolean(Constants.KEY_IS_COMMENT);
            commentCount = tweet.commentCount;
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tweet);
        binding.setHandler(handler);
        binding.setUser(UserRestful.INSTANCE.me());
        binding.setCommentCount(commentCount);
        binding.setIsComment(isComment);

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


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(new TweetDetailNavAdapter(getSupportFragmentManager(), this));

        viewPager.addOnPageChangeListener(new com.abcxo.android.ifootball.views.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                animComment(position == PageType.DETAIL.getIndex() ? true : false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        inputET = (EditText) findViewById(R.id.input);
        inputET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handler.onClickSend(inputET);
                    return true;
                }
                return false;
            }
        });

        commentTV = (TextView) findViewById(R.id.commentTV);
        titleTV = (TextView) findViewById(R.id.titleTV);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isComment && tweet != null &&
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
                            tweet.messages = messages;
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


    public void animComment(boolean visable) {
        if (visable) {
            titleTV.animate().alpha(0).setInterpolator(new DecelerateInterpolator(4)).setDuration(500).start();
            commentTV.animate().translationX(0).alpha(1).setInterpolator(new DecelerateInterpolator(4)).setDuration(500).start();
        } else {
            titleTV.animate().alpha(1).setInterpolator(new DecelerateInterpolator(4)).setDuration(500).start();
            commentTV.animate().translationX(-100).alpha(0).setInterpolator(new DecelerateInterpolator(4)).setDuration(500).start();
        }
    }

    public class BindingHandler {

        public void onClickItem(View view) {
            Message message = null;
            View parent = (View) view.getParent();
            if (parent.getId() == R.id.comment_item) {
                message = tweet.message();
            } else if (parent.getId() == R.id.comment_item1) {
                message = tweet.message1();
            } else if (parent.getId() == R.id.comment_item2) {
                message = tweet.message2();
            }
            if (message != null && message.uid != UserRestful.INSTANCE.meId()) {
                clearText(true, message);
            }
        }

        public void onClickComment(View view) {
            animComment(false);
            viewPager.setCurrentItem(PageType.COMMENT.getIndex());
        }


        public void onClickSend(View view) {
            if (UserRestful.INSTANCE.isLogin()) {
                String input = inputET.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    ViewUtils.toast(R.string.error_chat);
                } else {
                    long uid2 = selectedMessage != null ? selectedMessage.uid : isComment ? tweet.uid : tweetDetailFragment.getUid();
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
                                commentCount++;
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
