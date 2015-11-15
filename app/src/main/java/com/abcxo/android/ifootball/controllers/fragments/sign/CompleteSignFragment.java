package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentSignCompleteBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;


/**
 * Created by shadow on 15/11/4.
 */
public class CompleteSignFragment extends Fragment {

    private User user;

    private EditText nameET;
    private EditText signET;

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
        nameET = (EditText) view.findViewById(R.id.name);
        signET = (EditText) view.findViewById(R.id.sign);


    }

    public class BindingHandler {

        public void onClickAvatar(final View view) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CAMERA)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Constants.REQUEST_CAMERA);

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            Constants.REQUEST_CAMERA);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }


        }

        public void onClickComplete(final View view) {
            boolean isName = Utils.isName(nameET.getText().toString());
            boolean isSign = Utils.isSign(signET.getText().toString());
            if (!isName) {
                ViewUtils.toast(R.string.sign_login_name_error);
            } else if (!isSign) {
                ViewUtils.toast(R.string.sign_login_sign_error);
            } else {


            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
