package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.message.ChatMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.FocusMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.MessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.OtherMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.PromptMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.StarMessageFragment;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.restfuls.UserRestful;

import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.ALL;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.CHAT;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.COMMENT;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.FOCUS;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.OTHER;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.PROMPT;
import static com.abcxo.android.ifootball.controllers.fragments.nav.MessageNavFragment.SpinnerType.STAR;

public class MessageNavFragment extends NavFragment {

    //获取用户列表
    public enum SpinnerType {

        ALL(0),
        CHAT(1),
        COMMENT(2),
        PROMPT(3),
        FOCUS(4),
        STAR(5),
        OTHER(6);
        private int index;

        SpinnerType(int index) {
            this.index = index;
        }

        public static int size() {
            return SpinnerType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private BroadcastReceiver receiver;

    private MessageFragment allFg;
    private MessageFragment chatFg;
    private MessageFragment commentFg;
    private MessageFragment promptFg;
    private MessageFragment focusFg;
    private MessageFragment starFg;
    private MessageFragment otherFg;

    private Fragment currentFg;

    public static MessageNavFragment newInstance() {
        return newInstance(null);
    }

    public static MessageNavFragment newInstance(Bundle args) {
        MessageNavFragment fragment = new MessageNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Message message = intent.getParcelableExtra("message");
                allFg.refresh();
                MessageFragment fragment;
                switch (message.messageType) {
                    case FOCUS:
                        fragment = focusFg;
                        break;
                    case STAR:
                        fragment = starFg;
                        break;
                    case PROMPT:
                        fragment = promptFg;
                        break;
                    case COMMENT:
                        fragment = commentFg;
                        break;
                    case CHAT:
                        fragment = chatFg;
                        break;
                    default:
                        fragment = otherFg;
                        break;
                }

                if (fragment != null) {
                    fragment.refresh();
                }

            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_MESSAGE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Application.INSTANCE);
        localBroadcastManager.unregisterReceiver(receiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNavActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(7);

        MessageAdapter adapter = new MessageAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void toAll() {
        if (allFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            allFg = MessageFragment.newInstance(bundle);
        }
        toMessage(allFg);

    }


    private void toChat() {
        if (chatFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            chatFg = ChatMessageFragment.newInstance(bundle);
        }
        toMessage(chatFg);

    }

    private void toComment() {
        if (commentFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            commentFg = CommentMessageFragment.newInstance(bundle);
        }
        toMessage(commentFg);

    }


    private void toPrompt() {
        if (promptFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            promptFg = PromptMessageFragment.newInstance(bundle);
        }
        toMessage(promptFg);

    }


    private void toFocus() {
        if (focusFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            focusFg = FocusMessageFragment.newInstance(bundle);
        }
        toMessage(focusFg);

    }


    private void toStar() {
        if (starFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            starFg = StarMessageFragment.newInstance(bundle);
        }
        toMessage(starFg);

    }

    private void toOther() {
        if (otherFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());
            otherFg = OtherMessageFragment.newInstance(bundle);
        }
        toMessage(otherFg);

    }


    private void toMessage(Fragment fg) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fg.isAdded()) {
            transaction.add(R.id.content, fg);
        } else {
            transaction.show(fg);
        }
        if (currentFg != null) {
            transaction.hide(currentFg);
        }
        transaction.commit();
        currentFg = fg;


    }

    public class MessageAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MessageAdapter(FragmentManager fm, Context context) {
            super(fm);
            titles = context.getResources().getStringArray(R.array.message_page_list);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID2, UserRestful.INSTANCE.meId());

            if (position == ALL.getIndex()) {
                return MessageFragment.newInstance(bundle);
            } else if (position == CHAT.getIndex()) {
                return ChatMessageFragment.newInstance(bundle);
            } else if (position == COMMENT.getIndex()) {
                return CommentMessageFragment.newInstance(bundle);
            } else if (position == PROMPT.getIndex()) {
                return PromptMessageFragment.newInstance(bundle);
            } else if (position == FOCUS.getIndex()) {
                return FocusMessageFragment.newInstance(bundle);
            } else if (position == STAR.getIndex()) {
                return StarMessageFragment.newInstance(bundle);
            } else if (position == OTHER.getIndex()) {
                return OtherMessageFragment.newInstance(bundle);
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
    }


}
