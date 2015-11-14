package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

}
