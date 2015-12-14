package com.abcxo.android.ifootball.controllers.fragments.add;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.BR;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentAddTweetBinding;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.LocationUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;
import com.baidu.location.BDLocation;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 15/11/4.
 */
public class AddTweetFragment extends Fragment implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    public Tweet originTweet;

    public static AddTweetFragment newInstance() {
        return newInstance(null);
    }

    public static AddTweetFragment newInstance(Bundle args) {
        AddTweetFragment fragment = new AddTweetFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    private EditText inputET;
    private RecyclerView recyclerView;
    private AddTweetImageAdapter adapter;

    private Tweet tweet = new Tweet();

    private EmojiconsFragment emojiconsFragment;
    private View faceView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            originTweet = (Tweet) getArguments().get(Constants.KEY_TWEET);
            tweet.originTweet = originTweet;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAddTweetBinding binding = DataBindingUtil.bind(view);
        AddTweetFragment.BindingHandler handler = new BindingHandler();
        binding.setHandler(handler);
        binding.setUser(UserRestful.INSTANCE.me());
        binding.setTweet(tweet);

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
        recyclerView = (RecyclerView) view.findViewById(R.id.image_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddTweetImageAdapter(new ArrayList<Image>(), handler);
        recyclerView.setAdapter(adapter);

        faceView = view.findViewById(R.id.face_layout);

        emojiconsFragment = EmojiconsFragment.newInstance(true);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.face_layout, emojiconsFragment)
                .commit();

        inputET.requestFocus();

        inputET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faceView.getVisibility() == View.VISIBLE) {
                    hideFace(true);
                }
            }
        });
    }


    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(inputET, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(inputET);
    }


    public enum ImageType {
        NORMAL(0),
        ADD(1);
        private int index;

        ImageType(int index) {
            this.index = index;
        }

        public static int size() {
            return ImageType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }

    public class AddTweetImageAdapter extends RecyclerView.Adapter<AddTweetImageAdapter.BindingHolder> {

        public List<Image> images;

        private AddTweetFragment.BindingHandler handler;

        public AddTweetImageAdapter(List<Image> images, AddTweetFragment.BindingHandler handler) {
            this.images = images;
            this.handler = handler;
        }

        public void addImage(Bitmap bitmap) {
            String url = FileUtils.saveImage(bitmap, Constants.DIR_TWEET_ADD, Utils.randomString());
            if (!TextUtils.isEmpty(url)) {
                Image image = new Image();
                image.url = url;
                image.imageType = Image.ImageType.DELETE;
                images.add(image);
                notifyItemInserted(images.size());
            } else {
                ViewUtils.toast(R.string.add_tweet_send_image_error);
            }


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

        public View getItemLayoutView(ViewGroup parent, int type) {
            if (type == ImageType.NORMAL.getIndex()) {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image_normal, parent, false);
            } else {
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image_add, parent, false);
            }

        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == ImageType.NORMAL.getIndex()) {
                final Image image = images.get(position);
                holder.binding.setVariable(BR.image, image);
                holder.view.setTag(image);
            }
            holder.binding.setVariable(BR.handler, handler);

        }


        @Override
        public int getItemViewType(int position) {
            if (position < images.size()) {
                return ImageType.NORMAL.getIndex();
            } else {
                return ImageType.ADD.getIndex();
            }
        }


        @Override
        public int getItemCount() {
            return images.size() + 1;
        }


    }


    private void hideFace(boolean hidden) {
        if (hidden) {
            if (faceView.getVisibility() != View.GONE) {
                faceView.setVisibility(View.GONE);
                faceView.post(new Runnable() {
                    @Override
                    public void run() {
                        ViewUtils.openKeyboard(getActivity(), inputET);
                    }
                });
            }


        } else {
            if (faceView.getVisibility() != View.VISIBLE) {
                ViewUtils.closeKeyboard(getActivity());
                faceView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        faceView.setVisibility(View.VISIBLE);
                    }
                }, 100);
            }

        }
    }


    public class BindingHandler {

        public void onClickImage(final View view) {
            Image image = (Image) ((View) view.getParent()).getTag();
            NavUtils.toImage(getActivity(), (ArrayList<Image>) adapter.images, adapter.images.indexOf(image));
        }

        public void onClickAddImage(final View view) {
            if (adapter.images.size() < Constants.MAX_ADD_TWEET_IMAGE) {
                ViewUtils.image(AddTweetFragment.this);
            } else {
                ViewUtils.toast(R.string.add_tweet_send_image_max_error);
            }
        }

        public void onClickCamera(final View view) {
            ViewUtils.camera(AddTweetFragment.this);
        }

        public void onClickPhoto(final View view) {
            ViewUtils.photo(AddTweetFragment.this);
        }

        public void onClickLocation(final View view) {
            ViewUtils.loading(getActivity());
            LocationUtils.getLocation(new LocationUtils.LocationListener() {
                @Override
                public void onSuccess(BDLocation location) {
                    tweet.lon = location.getLongitude();
                    tweet.lat = location.getLatitude();
                    tweet.location = location.getLocationDescribe();
                    tweet.notifyPropertyChanged(BR.location);
                }

                @Override
                public void onError(String msg) {
                    ViewUtils.toast(msg);
                }

                @Override
                public void onFinish() {
                    ViewUtils.dismiss();
                }
            });

        }

        public void onClickFace(final View view) {
            hideFace(faceView.getVisibility() == View.VISIBLE);

        }

        public void onClickSend(final View view) {

            if (TextUtils.isEmpty(inputET.getText().toString()) && originTweet == null) {
                ViewUtils.toast(R.string.add_tweet_send_error);
            } else {
                tweet.uid = UserRestful.INSTANCE.meId();
                tweet.icon = UserRestful.INSTANCE.me().avatar;
                tweet.name = UserRestful.INSTANCE.me().name;
                tweet.content = inputET.getText().toString();
                ViewUtils.loading(getActivity());
                TweetRestful.INSTANCE.add(tweet, adapter.images, null, originTweet != null ? originTweet.id : 0, new TweetRestful.OnTweetRestfulGet() {
                    @Override
                    public void onSuccess(Tweet tweet) {
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_REFRESH_HOME));
                        ViewUtils.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.dismiss();
                        ViewUtils.toast(error.msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });

            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constants.REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
                String sdState = Environment.getExternalStorageState();
                if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                if (ViewUtils.imageUrl != null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), ViewUtils.imageUrl);
                    adapter.addImage(bitmap);
                }


            } else if (requestCode == Constants.REQUEST_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    adapter.addImage(bitmap);
                } catch (Exception e) {
                    ViewUtils.toast(R.string.add_tweet_send_image_error);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void finish() {
        FileUtils.delete(Constants.DIR_TWEET_ADD);
        getActivity().finish();
    }

    public boolean onBackPressed() {
        if (faceView.getVisibility() == View.VISIBLE) {
            hideFace(true);
            return true;
        }
        return false;
    }
}

