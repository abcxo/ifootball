package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.fragments.message.ChatUserMessageFragment;
import com.abcxo.android.ifootball.databinding.FragmentDetailChatBinding;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.restfuls.MessageRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class ChatDetailFragment extends DetailFragment {

    private long uid;
    private long uid2;
    private EditText inputET;
    private ChatUserMessageFragment chatUserMessageFragment;

    public static ChatDetailFragment newInstance() {
        return newInstance(null);
    }

    public static ChatDetailFragment newInstance(Bundle args) {
        ChatDetailFragment fragment = new ChatDetailFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uid = args.getLong(Constants.KEY_UID);
            uid2 = args.getLong(Constants.KEY_UID2);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDetailChatBinding binding = DataBindingUtil.bind(view);
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


        inputET = (EditText) view.findViewById(R.id.input);

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_UID, uid);
        bundle.putLong(Constants.KEY_UID2, uid2);
        chatUserMessageFragment = ChatUserMessageFragment.newInstance(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.comment, chatUserMessageFragment).commit();


    }


    public class BindingHandler {

        public void onClickSend(View view) {
            String input = inputET.getText().toString();
            if (TextUtils.isEmpty(input)) {
                ViewUtils.toast(R.string.error_chat);
            } else {
                Message message = new Message();
                message.uid = UserRestful.INSTANCE.meId();
                message.uid2 = UserRestful.INSTANCE.meId() == uid ? uid2 : uid;
                message.title = UserRestful.INSTANCE.me().name;
                message.icon = UserRestful.INSTANCE.me().avatar;
                message.content = input;
                message.messageType = Message.MessageType.CHAT;
                message.mainType = Message.MessageMainType.CHAT_ME;
                message.detailType = Message.MessageDetailType.NONE;
                message.time = Utils.time();
                List<Message> messages = new ArrayList<>();
                messages.add(message);
                chatUserMessageFragment.addMessages(messages);
                inputET.getText().clear();
                ViewUtils.closeKeyboard(getActivity());
                MessageRestful.INSTANCE.chat(message, new MessageRestful.OnMessageRestfulDo() {
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

        }
    }
}
