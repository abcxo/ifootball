package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.abcxo.android.ifootball.Application;
import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.activities.ContactActivity;
import com.abcxo.android.ifootball.controllers.fragments.CommonFragment;
import com.abcxo.android.ifootball.databinding.FragmentSettingBinding;
import com.abcxo.android.ifootball.models.User;
import com.abcxo.android.ifootball.restfuls.RestfulError;
import com.abcxo.android.ifootball.restfuls.UserRestful;
import com.abcxo.android.ifootball.utils.FileUtils;
import com.abcxo.android.ifootball.utils.NavUtils;
import com.abcxo.android.ifootball.utils.Utils;
import com.abcxo.android.ifootball.utils.ViewUtils;

/**
 * Created by shadow on 15/12/11.
 */
public class SettingFragment extends CommonFragment {

    public static SettingFragment newInstance() {
        return newInstance(null);
    }

    public static SettingFragment newInstance(Bundle args) {
        SettingFragment fragment = new SettingFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    enum RequestType {
        COVER,
        AVATAR
    }

    private User user;

    private RequestType requestType;
    private FragmentSettingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserRestful.INSTANCE.me();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        binding.setUser(user);
        binding.setHandler(new BindingHandler());
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


    private void edit(final UserRestful.OnUserRestfulGet onGet) {
        ViewUtils.loading(getActivity());
        UserRestful.INSTANCE.edit(user, new UserRestful.OnUserRestfulGet() {
            @Override
            public void onSuccess(User user) {
                LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_EDIT));
                refresh();
                onGet.onSuccess(user);
            }

            @Override
            public void onError(RestfulError error) {
                ViewUtils.toast(error.msg);
                onGet.onError(error);
            }

            @Override
            public void onFinish() {
                ViewUtils.dismiss();
                onGet.onFinish();
            }
        });
    }


    private void cover(Bitmap image) {
        ViewUtils.loading(getActivity());
        UserRestful.INSTANCE.cover(image, new UserRestful.OnUserRestfulGet() {
            @Override
            public void onSuccess(User user) {
                LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_EDIT));
                refresh();
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

    private void avatar(Bitmap image) {
        ViewUtils.loading(getActivity());
        UserRestful.INSTANCE.avatar(image, new UserRestful.OnUserRestfulGet() {
            @Override
            public void onSuccess(User user) {
                LocalBroadcastManager.getInstance(Application.INSTANCE).sendBroadcast(new Intent(Constants.ACTION_EDIT));
                refresh();
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

    public void refresh() {
        user = UserRestful.INSTANCE.me();
        binding.setUser(user);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constants.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
                String sdState = Environment.getExternalStorageState();
                if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                if (ViewUtils.imageUrl != null) {
                    Bitmap bitmap = ViewUtils.getSmallBitmap(FileUtils.uri2Path(ViewUtils.imageUrl));
                    if (requestType == RequestType.COVER) {
                        cover(bitmap);
                    } else {
                        avatar(bitmap);
                    }
                }

            } else if (requestCode == Constants.REQUEST_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    if (requestType == RequestType.COVER) {
                        cover(bitmap);
                    } else {
                        avatar(bitmap);
                    }
                } catch (Throwable e) {
                    ViewUtils.toast(R.string.add_tweet_send_image_error);
                }

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    public class BindingHandler {

        public void onClickCover(View view) {
            requestType = RequestType.COVER;
            ViewUtils.image(SettingFragment.this);
        }

        public void onClickMe(View view) {
            NavUtils.toUserDetail(view.getContext(), UserRestful.INSTANCE.meId());
        }

        public void onClickContact(View view) {
            Intent intent = new Intent(view.getContext(), ContactActivity.class);
            view.getContext().startActivity(intent);
        }


        public void onClickAvatar(View view) {
            requestType = RequestType.AVATAR;
            ViewUtils.image(SettingFragment.this);
        }

        public void onClickName(View view) {
            View inputView = View.inflate(view.getContext(), R.layout.view_input, null);
            TextView titleTV = (TextView) inputView.findViewById(R.id.title);
            titleTV.setText(R.string.setting_name_text);
            final EditText editText = (EditText) inputView.findViewById(R.id.input);
            editText.setHint(R.string.setting_name_hint);
            editText.setText(user.name);
            new AlertDialog.Builder(getActivity())
                    .setView(inputView)
                    .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isName = Utils.isName(editText.getText().toString());
                            if (isName) {
                                final String name = user.name;
                                user.name = editText.getText().toString();
                                edit(new UserRestful.OnUserRestfulGet() {
                                    @Override
                                    public void onSuccess(User user) {

                                    }

                                    @Override
                                    public void onError(RestfulError error) {
                                        user.name = name;
                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });
                            } else {
                                ViewUtils.toast(R.string.sign_login_name_error);
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel_text, null)
                    .show();
        }

        public void onClickSign(View view) {
            View inputView = View.inflate(view.getContext(), R.layout.view_input, null);
            TextView titleTV = (TextView) inputView.findViewById(R.id.title);
            titleTV.setText(R.string.setting_sign_text);
            final EditText editText = (EditText) inputView.findViewById(R.id.input);
            editText.setHint(R.string.setting_sign_hint);
            editText.setText(user.sign);
            new AlertDialog.Builder(getActivity())
                    .setView(inputView)
                    .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isSign = Utils.isSign(editText.getText().toString());
                            if (isSign) {
                                final String sign = user.sign;
                                user.sign = editText.getText().toString();
                                edit(new UserRestful.OnUserRestfulGet() {
                                    @Override
                                    public void onSuccess(User user) {

                                    }

                                    @Override
                                    public void onError(RestfulError error) {
                                        user.sign = sign;
                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });
                            } else {
                                ViewUtils.toast(R.string.sign_login_sign_error);
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel_text, null)
                    .show();
        }

        public void onClickPosition(View view) {
            View inputView = View.inflate(view.getContext(), R.layout.view_input, null);
            TextView titleTV = (TextView) inputView.findViewById(R.id.title);
            titleTV.setText(R.string.setting_position_text);
            final EditText editText = (EditText) inputView.findViewById(R.id.input);
            editText.setHint(R.string.setting_position_hint);
            editText.setText(user.position);
            new AlertDialog.Builder(getActivity())
                    .setView(inputView)
                    .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isPosition = Utils.isPosition(editText.getText().toString());
                            if (isPosition) {
                                final String position = user.position;
                                user.position = editText.getText().toString();
                                edit(new UserRestful.OnUserRestfulGet() {
                                    @Override
                                    public void onSuccess(User user) {

                                    }

                                    @Override
                                    public void onError(RestfulError error) {
                                        user.position = position;
                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });
                            } else {
                                ViewUtils.toast(R.string.sign_login_position_error);
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel_text, null)
                    .show();
        }

        public void onClickGender(View view) {
            new AlertDialog.Builder(getActivity())
                    .setItems(R.array.gender_list, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final User.GenderType gender = user.gender;
                            user.gender = which == 0 ? User.GenderType.MALE : User.GenderType.FEMALE;
                            edit(new UserRestful.OnUserRestfulGet() {
                                @Override
                                public void onSuccess(User user) {

                                }

                                @Override
                                public void onError(RestfulError error) {
                                    user.gender = gender;
                                }

                                @Override
                                public void onFinish() {

                                }
                            });
                            dialog.dismiss();
                        }
                    }).show();
        }

        public void onClickLogout(View view) {
            ViewUtils.loading(view.getContext());
            UserRestful.INSTANCE.logout(new UserRestful.OnUserRestfulGet() {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onError(RestfulError error) {

                }

                @Override
                public void onFinish() {
                    ViewUtils.dismiss();
                    getActivity().finish();
                }
            });

        }


    }
}
