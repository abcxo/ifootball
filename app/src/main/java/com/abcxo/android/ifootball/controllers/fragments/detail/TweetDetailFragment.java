package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.SearchAdapter;
import com.abcxo.android.ifootball.controllers.fragments.add.AddTweetFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentTweetMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.MessageFragment;
import com.abcxo.android.ifootball.databinding.FragmentDetailTweetBinding;
import com.abcxo.android.ifootball.databinding.FragmentDetailUserBinding;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.MessageRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class TweetDetailFragment extends DetailFragment {

    private Tweet tweet;
    private long tid;
    private FragmentDetailTweetBinding binding;

    private EditText inputET;

    private WebView webView;
    private CommentTweetMessageFragment commentTweetMessageFragment;

    private Message selectedMessage;

    public static TweetDetailFragment newInstance() {
        return newInstance(null);
    }

    public static TweetDetailFragment newInstance(Bundle args) {
        TweetDetailFragment fragment = new TweetDetailFragment();
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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tweet, container, false);
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
        View comment = view.findViewById(R.id.comment);
        ViewGroup.LayoutParams lp = comment.getLayoutParams();
        lp.height = ViewUtils.screenHeight();
        comment.setLayoutParams(lp);

        if (tweet != null) {
            bindData();
        } else {
            ViewUtils.loading(getActivity());
            TweetRestful.INSTANCE.get(tid, new TweetRestful.OnTweetRestfulGet() {
                @Override
                public void onSuccess(Tweet tweet) {
                    TweetDetailFragment.this.tweet = tweet;
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
            });

        }

        inputET = (EditText) view.findViewById(R.id.input);

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_TID, tweet != null ? tweet.id : tid);
        commentTweetMessageFragment = CommentTweetMessageFragment.newInstance(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.comment, commentTweetMessageFragment).commit();
        commentTweetMessageFragment.setListener(new MessageFragment.Listener() {
            @Override
            public void onItemClick(View view, Message message, int position) {
                if (message.uid != UserRestful.INSTANCE.meId()) {
                    clearText(true, message);
                }

            }
        });

        inputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl(tweet.content);

    }


    public void bindData() {
        binding.setTweet(tweet);

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
                    List<Message> messages = new ArrayList<>();
                    messages.add(message);
                    commentTweetMessageFragment.addMessages(messages);

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
