package com.abcxo.android.ifootball.controllers.fragments.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.MessageAdapter;
import com.abcxo.android.ifootball.models.Message;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.MessageRestful;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.views.DividerItemDecoration;
import com.abcxo.android.ifootball.views.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {


    protected List<Message> list = new ArrayList<>();

    protected SwipeRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;
    protected MessageAdapter adapter;
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

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (listener != null) {
                            listener.onItemClick(view,list.get(position),position);
                        }
                    }
                })
        );

        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MessageRestful.INSTANCE.gets(uid, uid2, tid, getGetsType(), 0, new MessageRestful.OnMessageRestfulList() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        refreshMessages(messages);
                    }

                    @Override
                    public void onError(RestfulError error) {

                    }

                    @Override
                    public void onFinish() {
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        };
        refreshLayout.setOnRefreshListener(listener);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });


    }


    protected MessageRestful.GetsType getGetsType() {
        return MessageRestful.GetsType.ALL;
    }

    protected void refreshMessages(List<Message> messages) {
        list.clear();
        list.addAll(messages);
        adapter.notifyDataSetChanged();
    }

    public void addMessages(List<Message> messages) {
        int bCount = list.size();
        list.addAll(messages);
        adapter.notifyItemRangeInserted(bCount, messages.size());
    }

    public interface Listener {
        void onItemClick(View view, Message message,int position);
    }

}
