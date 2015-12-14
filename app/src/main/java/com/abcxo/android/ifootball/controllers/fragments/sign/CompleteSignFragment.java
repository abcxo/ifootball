package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentSignCompleteBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.models.User.GenderType;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;


/**
 * Created by shadow on 15/11/4.
 */
public class CompleteSignFragment extends Fragment {

    private User user;

    private EditText nameET;
    private EditText signET;
    private ImageView avatarIV;
    private RadioGroup genderRG;

    private Bitmap image;

    public static CompleteSignFragment newInstance() {
        return newInstance(null);
    }

    public static CompleteSignFragment newInstance(Bundle args) {
        CompleteSignFragment fragment = new CompleteSignFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getParcelable(Constants.KEY_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_complete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignCompleteBinding binding = DataBindingUtil.bind(view);
        binding.setHandler(new BindingHandler());
        avatarIV = (ImageView) view.findViewById(R.id.avatar);
        nameET = (EditText) view.findViewById(R.id.name);
        signET = (EditText) view.findViewById(R.id.sign);
        genderRG = (RadioGroup) view.findViewById(R.id.gender_layout);

    }

    public class BindingHandler {

        public void onClickAvatar(final View view) {
            ViewUtils.image(CompleteSignFragment.this);
        }

        public void onClickComplete(final View view) {
            boolean isName = Utils.isName(nameET.getText().toString());
            boolean isSign = Utils.isSign(signET.getText().toString());
            if (!isName) {
                ViewUtils.toast(R.string.sign_login_name_error);
            } else if (!isSign) {
                ViewUtils.toast(R.string.sign_login_sign_error);
            } else {
                user.name = nameET.getText().toString();
                user.sign = signET.getText().toString();
                user.gender = genderRG.getCheckedRadioButtonId() == R.id.male ? GenderType.MALE : GenderType.FEMALE;
                ViewUtils.loading(getActivity());
                UserRestful.INSTANCE.edit(user, new UserRestful.OnUserRestfulGet() {
                    @Override
                    public void onSuccess(User user) {
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_EDIT));
                        if (image != null) {
                            UserRestful.INSTANCE.avatar(image, new UserRestful.OnUserRestfulGet() {
                                @Override
                                public void onSuccess(User user) {
                                    LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_EDIT));
                                    ViewUtils.dismiss();
                                    getActivity().finish();
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
                        } else {
                            ViewUtils.dismiss();
                            getActivity().finish();
                        }


                    }

                    @Override
                    public void onError(RestfulError error) {
                        ViewUtils.toast(error.msg);
                        ViewUtils.dismiss();
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
                    avatarIV.setImageBitmap(bitmap);
                    image = bitmap;
                }


            } else if (requestCode == Constants.REQUEST_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    avatarIV.setImageBitmap(bitmap);
                    image = bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
