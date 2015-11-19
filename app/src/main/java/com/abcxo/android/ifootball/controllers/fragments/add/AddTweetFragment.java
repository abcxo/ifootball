package com.abcxo.android.ifootball.controllers.fragments.add;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.AddTweetImageAdapter;
import com.abcxo.android.ifootball.controllers.fragments.main.TweetFragment;
import com.abcxo.android.ifootball.databinding.FragmentAddTweetBinding;
import com.abcxo.android.ifootball.models.Image;
import com.abcxo.android.ifootball.models.Tweet;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.TweetRestful;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by shadow on 15/11/4.
 */
public class AddTweetFragment extends Fragment {
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            originTweet = (Tweet) getArguments().get(Constants.KEY_TWEET);
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
        binding.setTweet(originTweet);

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
        if (originTweet == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.image_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new AddTweetImageAdapter(new ArrayList<Image>(), handler);
            recyclerView.setAdapter(adapter);

        }

    }


    public class BindingHandler {


        public void onClickImage(final View view) {
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

        public void onClickSend(final View view) {

            if (TextUtils.isEmpty(inputET.getText().toString())) {
                ViewUtils.toast(R.string.add_tweet_send_error);
            } else {
                Tweet tweet = new Tweet();
                tweet.uid = UserRestful.INSTANCE.meId();
                tweet.icon = UserRestful.INSTANCE.me().avatar;
                tweet.name = UserRestful.INSTANCE.me().name;
                tweet.title = tweet.name;
                tweet.content = inputET.getText().toString();
                tweet.summary = tweet.content;

                ViewUtils.loading(getActivity());
                TweetRestful.INSTANCE.add(tweet, hasImage() ? adapter.images : null, null, originTweet != null ? originTweet.id : 0, new TweetRestful.OnTweetRestfulGet() {
                    @Override
                    public void onSuccess(Tweet tweet) {
                        finish();
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
        }

    }

    private boolean hasImage() {
        return adapter != null && adapter.images.size() > 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            adapter.addImage(bitmap);

        } else if (requestCode == Constants.REQUEST_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                adapter.addImage(bitmap);
            } catch (Exception e) {
                ViewUtils.toast(R.string.add_tweet_send_image_error);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Constants.REQUEST_CAMERA);
        }
    }

    private void finish() {
        FileUtils.delete(Constants.DIR_ADD_TWEET);
        getActivity().finish();
    }
}

