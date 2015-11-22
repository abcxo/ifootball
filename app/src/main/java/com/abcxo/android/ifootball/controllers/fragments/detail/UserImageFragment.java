package com.abcxo.android.ifootball.controllers.fragments.detail;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.TweetAdapter;
import com.abcxo.android.ifootball.controllers.adapters.UserImageAdapter;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.abcxo.android.ifootball.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class UserImageFragment extends Fragment {


    protected long uid;

    protected SwipeRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;
    protected UserImageAdapter adapter;

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

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new UserImageAdapter(new ArrayList<Image>(), new BindingHandler());
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2, R.color.color_refresh_3, R.color.color_refresh_4);

        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetRestful.INSTANCE.gets(uid, getGetsType(), 0, new TweetRestful.OnTweetRestfulList() {
                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        refreshImages(tweetsToImages(tweets));
                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.toast(error.msg);
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
        adapter.images.addAll(images);
        adapter.notifyDataSetChanged();
    }

    protected void addImages(List<Image> images) {
        int bCount = adapter.images.size();
        adapter.images.addAll(images);
        adapter.notifyItemRangeInserted(bCount, images.size());
    }


    public class BindingHandler {
        public void onClickImage(View view) {
            ViewDataBinding binding = DataBindingUtil.findBinding(view);
            Image image = (Image) binding.getRoot().getTag();
            NavUtils.toImage(view.getContext(), (ArrayList<Image>) adapter.images, adapter.images.indexOf(image));
        }
    }


}
