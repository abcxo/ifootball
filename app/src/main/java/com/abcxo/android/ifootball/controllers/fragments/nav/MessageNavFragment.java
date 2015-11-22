package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.SpinnerAdapter;
import com.abcxo.android.ifootball.controllers.fragments.message.ChatMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.CommentMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.FocusMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.MessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.OtherMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.PromptMessageFragment;
import com.abcxo.android.ifootball.controllers.fragments.message.StarMessageFragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerAdapter(
                toolbar.getContext(),
                getActivity().getResources().getStringArray(R.array.message_page_list)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == ALL.getIndex()) {
                    toAll();
                } else if (position == CHAT.getIndex()) {
                    toChat();
                } else if (position == COMMENT.getIndex()) {
                    toComment();
                } else if (position == PROMPT.getIndex()) {
                    toPrompt();
                } else if (position == FOCUS.getIndex()) {
                    toFocus();
                } else if (position == STAR.getIndex()) {
                    toStar();
                } else if (position == OTHER.getIndex()) {
                    toOther();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void toAll() {
        if (allFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            allFg = MessageFragment.newInstance(bundle);
        }
        toMessage(allFg);

    }


    private void toChat() {
        if (chatFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            chatFg = ChatMessageFragment.newInstance(bundle);
        }
        toMessage(chatFg);

    }

    private void toComment() {
        if (commentFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            commentFg = CommentMessageFragment.newInstance(bundle);
        }
        toMessage(commentFg);

    }


    private void toPrompt() {
        if (promptFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            promptFg = PromptMessageFragment.newInstance(bundle);
        }
        toMessage(promptFg);

    }


    private void toFocus() {
        if (focusFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            focusFg = FocusMessageFragment.newInstance(bundle);
        }
        toMessage(focusFg);

    }


    private void toStar() {
        if (starFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            starFg = StarMessageFragment.newInstance(bundle);
        }
        toMessage(starFg);

    }

    private void toOther() {
        if (otherFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
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


}
