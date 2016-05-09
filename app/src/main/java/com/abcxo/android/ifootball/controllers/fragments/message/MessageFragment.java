package com.abcxo.android.ifootball.controllers.fragments.message;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.restfuls.MessageRestful;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.DividerItemDecoration;
import com.abcxo.android.ifootball.views.RecyclerItemClickListener;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT_GROUP;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT_ME;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.CHAT_USER;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.COMMENT;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.COMMENT_TWEET;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.FOCUS;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.NORMAL;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.PROMPT;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.SPECIAL;
import static com.abcxo.android.ifootball.models.Message.MessageMainType.STAR;

public class MessageFragment extends Fragment {

    protected List<Message> list = new ArrayList<>();
    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    protected SuperRecyclerView recyclerView;
    protected int pageIndex;

    public MessageAdapter adapter;
    private Listener listener;

    protected long uid;
    protected long uid2;
    protected long tid;


    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static MessageFragment newInstance() {
        return newInstance(null);
    }

    public static MessageFragment newInstance(Bundle args) {
        MessageFragment fragment = new MessageFragment();
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
            tid = args.getLong(Constants.KEY_TID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.setRefreshingColorResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                loadData(true);
            }
        };
        recyclerView.setRefreshListener(onRefreshListener);

        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                loadData(false);
            }
        }, Constants.MAX_LEFT_MORE);
        load();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (listener != null) {
                            listener.onItemClick(view, list.get(position), position);
                        }
                    }
                })
        );

    }

    protected void loadData(final boolean first) {
        MessageRestful.INSTANCE.gets(getGetsType(), uid, uid2, tid, pageIndex, new MessageRestful.OnMessageRestfulList() {
            @Override
            public void onSuccess(List<Message> messages) {
                if (first) {
                    refreshMessages(messages);
                    FileUtils.setObject(getKey(), new ArrayList<>(messages));
                } else {
                    addMessages(messages);
                }

            }

            @Override
            public void onError(RestfulError error) {
                ViewUtils.toast(error.msg);
            }

            @Override
            public void onFinish() {
                if (first) {
                    recyclerView.getSwipeToRefresh().setRefreshing(false);
                } else {
                    recyclerView.hideMoreProgress();
                }


            }
        });
    }


    protected void load() {
        ArrayList<Message> messages = (ArrayList<Message>) FileUtils.getObject(getKey());
        if (messages != null && messages.size() > 0) {
            refreshMessages(messages);

        }
        refresh();


    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;uid2=%s;tid=%s;getsType=%s;", uid, uid2, tid, getGetsType().name()));
    }

    public void refresh() {
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getSwipeToRefresh().setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
    }

    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.ALL;
    }

    protected void refreshMessages(List<Message> messages) {
        list.clear();


        if (messages != null && messages.size() > 0) {
            list.addAll(messages);
            pageIndex++;

        }
        adapter.notifyDataSetChanged();
        if (listener != null) {
            listener.onLoaded(list);
        }
    }


    public void addMessages(List<Message> messages) {
        if (messages != null && messages.size() > 0) {
            int bCount = list.size();
            list.addAll(messages);
            adapter.notifyItemRangeInserted(bCount, messages.size());
            if (listener != null) {
                listener.onLoaded(list);
            }
            pageIndex++;
        }
    }

    public void addMessage(Message message) {
        if (message != null) {
            int bCount = list.size();
            list.add(message);
            adapter.notifyItemRangeInserted(bCount, 1);
            if (listener != null) {
                listener.onLoaded(list);
            }
        }
    }


    public void insertMessage(Message message) {
        if (message != null) {
            list.add(0, message);
            adapter.notifyItemRangeInserted(0, 1);
            if (listener != null) {
                listener.onLoaded(list);
            }
        }
    }

    public interface Listener {
        void onLoaded(List<Message> messages);

        void onItemClick(View view, Message message, int position);
    }


    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.BindingHolder> {

        public List<Message> messages;

        public MessageAdapter(List<Message> users) {
            this.messages = users;
        }

        public class BindingHolder extends RecyclerView.ViewHolder {
            public ViewDataBinding binding;

            public BindingHolder(View rowView) {
                super(rowView);
                binding = DataBindingUtil.bind(rowView);
            }
        }

        @Override
        public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
            BindingHolder holder = new BindingHolder(getItemLayoutView(parent, type));
            return holder;
        }


        public View getItemLayoutView(ViewGroup parent, int type) {
            if (type == NORMAL.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_normal, parent, false);
            } else if (type == FOCUS.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_focus, parent, false);
            } else if (type == COMMENT.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_comment, parent, false);
            } else if (type == PROMPT.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_prompt, parent, false);
            } else if (type == STAR.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_star, parent, false);
            } else if (type == CHAT_GROUP.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_chat_group, parent, false);
            } else if (type == SPECIAL.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_special, parent, false);
            } else if (type == COMMENT_TWEET.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_comment_tweet, parent, false);
            } else if (type == CHAT_USER.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_chat_user, parent, false);
            } else if (type == CHAT_ME.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_chat_me, parent, false);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            Message message = messages.get(position);
            holder.binding.setVariable(BR.message, message);

        }


        @Override
        public int getItemViewType(int position) {
            Message message = messages.get(position);
            Message.MessageMainType type = message.mainType;
            if (type == Message.MessageMainType.CHAT_USER && message.uid == UserRestful.INSTANCE.meId()) {
                type = Message.MessageMainType.CHAT_ME;
            }

            return type.getIndex();


        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

    }

}
