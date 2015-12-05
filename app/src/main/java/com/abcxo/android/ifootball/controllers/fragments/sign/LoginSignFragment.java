package com.abcxo.android.ifootball.controllers.fragments.sign;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.databinding.FragmentSignLoginBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

/**
 * Created by shadow on 15/11/4.
 */
public class LoginSignFragment extends Fragment {

    public EditText emailET;
    public EditText passwordET;

    public static LoginSignFragment newInstance() {
        return newInstance(null);
    }

    public static LoginSignFragment newInstance(Bundle args) {
        LoginSignFragment fragment = new LoginSignFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignLoginBinding binding = DataBindingUtil.bind(view);
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

        emailET = (EditText) view.findViewById(R.id.email);
        passwordET = (EditText) view.findViewById(R.id.password);


    }

    public class BindingHandler {
        public void onClickLogin(final View view) {
            boolean isEmail = Utils.isEmail(emailET.getText().toString());
            boolean isPassword = Utils.isPassword(passwordET.getText().toString());
            if (!isEmail) {
                ViewUtils.toast(R.string.sign_login_email_error);
            } else if (!isPassword) {
                ViewUtils.toast(R.string.sign_login_password_error);
            } else {
                ViewUtils.loading(getActivity());
                UserRestful.INSTANCE.login(emailET.getText().toString(), Utils.md52(passwordET.getText().toString()), new UserRestful.OnUserRestfulGet() {
                    @Override
                    public void onSuccess(User user) {
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_LOGIN));
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

            }
        }

        public void onClickRegister(final View view) {
            boolean isEmail = Utils.isEmail(emailET.getText().toString());
            boolean isPassword = Utils.isPassword(passwordET.getText().toString());
            if (!isEmail) {
                ViewUtils.toast(R.string.sign_login_email_error);
            } else if (!isPassword) {
                ViewUtils.toast(R.string.sign_login_password_error);
            } else {
                ViewUtils.loading(getActivity());
                UserRestful.INSTANCE.register(emailET.getText().toString(), Utils.md52(passwordET.getText().toString()), new UserRestful.OnUserRestfulGet() {
                    @Override
                    public void onSuccess(User user) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.KEY_USER, user);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content, CompleteSignFragment.newInstance(bundle))
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_LOGIN));
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

        public void onClickQQ(View view) {
            Toast.makeText(getActivity(), "暂时不支持", Toast.LENGTH_SHORT).show();
        }

        public void onClickWechat(View view) {
            Toast.makeText(getActivity(), "暂时不支持", Toast.LENGTH_SHORT).show();
        }

        public void onClickWeibo(View view) {
            Toast.makeText(getActivity(), "暂时不支持", Toast.LENGTH_SHORT).show();
        }

        public void onClickForgetPassword(View view) {
            Toast.makeText(getActivity(), "暂时不支持", Toast.LENGTH_SHORT).show();
        }

    }
}
