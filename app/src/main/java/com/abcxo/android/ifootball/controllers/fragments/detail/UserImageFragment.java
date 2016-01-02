package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserImageFragment extends Fragment {


    protected long uid;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    protected SuperRecyclerView recyclerView;
    protected UserImageAdapter adapter;

    protected int pageIndex;

    public static UserImageFragment newInstance() {
        return newInstance(null);
    }

    public static UserImageFragment newInstance(Bundle args) {
        UserImageFragment fragment = new UserImageFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uid = args.getLong(Constants.KEY_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_user, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserImageAdapter(new ArrayList<Image>(), new BindingHandler());
        recyclerView.setAdapter(adapter);

        recyclerView.setNumberBeforeMoreIsCalled(Constants.MAX_LEFT_MORE);
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
    }

    protected void load() {
        ArrayList<Tweet> tweets = (ArrayList<Tweet>) FileUtils.getObject(getKey());
        if (tweets != null&&tweets.size()>0) {
            refreshImages(tweetsToImages(tweets));

        }
        refresh();

    }

    protected String getKey() {
        return Utils.md5(String.format("uid=%s;getsType=%s;", uid, getGetsType().name()));
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

    protected void loadData(final boolean first) {
        TweetRestful.INSTANCE.gets(getGetsType(), uid, "",
                pageIndex, new TweetRestful.OnTweetRestfulList() {
                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        if (first) {
                            refreshImages(tweetsToImages(tweets));
                            FileUtils.setObject(getKey(), new ArrayList<>(tweets));
                        } else {
                            addImages(tweetsToImages(tweets));
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


    protected TweetRestful.GetsType getGetsType() {
        return TweetRestful.GetsType.USER;
    }


    private List<Image> tweetsToImages(List<Tweet> tweets) {
        List<Image> images = new ArrayList<>();
        for (Tweet tweet : tweets) {
            images.addAll(tweet.imageList());
        }
        return images;
    }

    protected void refreshImages(List<Image> images) {
        adapter.images.clear();


        if (images != null && images.size() > 0) {
            adapter.images.addAll(images);
            pageIndex++;
        }
        adapter.notifyDataSetChanged();
    }

    protected void addImages(List<Image> images) {
        if (images != null && images.size() > 0) {
            int bCount = adapter.images.size();
            adapter.images.addAll(images);
            adapter.notifyItemRangeInserted(bCount, images.size());
            pageIndex++;
        }
    }


    public class UserImageAdapter extends RecyclerView.Adapter<UserImageAdapter.BindingHolder> {

        public List<Image> images;
        public BindingHandler handler;

        public UserImageAdapter(List<Image> images, UserImageFragment.BindingHandler handler) {
            this.images = images;
            this.handler = handler;
        }

        public class BindingHolder extends RecyclerView.ViewHolder {
            public ViewDataBinding binding;
            public View view;

            public BindingHolder(View rowView) {
                super(rowView);
                binding = DataBindingUtil.bind(rowView);
                view = rowView;
            }
        }

        @Override
        public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
            BindingHolder holder = new BindingHolder(getItemLayoutView(parent, type));

            return holder;
        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            Image image = images.get(position);
            holder.binding.setVariable(BR.image, image);
            holder.binding.setVariable(BR.handler, handler);
            holder.view.setTag(image);
        }

        public View getItemLayoutView(ViewGroup parent, int type) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_user, parent, false);

        }


        @Override
        public int getItemCount() {
            return images.size();
        }

    }

    public class BindingHandler {
        public void onClickImage(View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            Image image = (Image) binding.getRoot().getTag();
            NavUtils.toImage(view.getContext(), (ArrayList<Image>) adapter.images, adapter.images.indexOf(image));
        }
    }


}
