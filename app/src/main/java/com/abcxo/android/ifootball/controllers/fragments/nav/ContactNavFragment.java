package com.abcxo.android.ifootball.controllers.fragments.nav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.abcxo.android.ifootball.R;
import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.controllers.adapters.SpinnerAdapter;
import com.abcxo.android.ifootball.controllers.fragments.contact.ContactUserFragment;
import com.abcxo.android.ifootball.controllers.fragments.contact.FansContactFragment;
import com.abcxo.android.ifootball.controllers.fragments.contact.FocusContactFragment;
import com.abcxo.android.ifootball.controllers.fragments.contact.FriendContactFragment;
import com.abcxo.android.ifootball.restfuls.UserRestful;

import static com.abcxo.android.ifootball.controllers.fragments.nav.ContactNavFragment.SpinnerType.FANS;
import static com.abcxo.android.ifootball.controllers.fragments.nav.ContactNavFragment.SpinnerType.FOCUS;
import static com.abcxo.android.ifootball.controllers.fragments.nav.ContactNavFragment.SpinnerType.FRIEND;


public class ContactNavFragment extends NavFragment {

    //获取用户列表
    public enum SpinnerType {

        FRIEND(0),
        FOCUS(1),
        FANS(2);
        private int index;

        SpinnerType(int index) {
            this.index = index;
        }

        public static int size() {
            return SpinnerType.values().length;
        }

        public int getIndex() {
            return index;
        }
    }


    private ContactUserFragment friendFg;
    private ContactUserFragment focusFg;
    private ContactUserFragment fansFg;

    private Fragment currentFg;

    public static ContactNavFragment newInstance() {
        return newInstance(null);
    }

    public static ContactNavFragment newInstance(Bundle args) {
        ContactNavFragment fragment = new ContactNavFragment();
        if (args != null) fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerAdapter(
                toolbar.getContext(),
                getActivity().getResources().getStringArray(R.array.contact_page_list)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == FRIEND.getIndex()) {
                    toFriend();
                } else if (position == FOCUS.getIndex()) {
                    toFocus();
                } else if (position == FANS.getIndex()) {
                    toFans();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void toFriend() {
        if (friendFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            friendFg = FriendContactFragment.newInstance(bundle);
        }
        toContact(friendFg);

    }

    private void toFocus() {
        if (focusFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            focusFg = FocusContactFragment.newInstance(bundle);

        }
        toContact(focusFg);

    }

    private void toFans() {
        if (fansFg == null) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.KEY_UID, UserRestful.INSTANCE.meId());
            fansFg = FansContactFragment.newInstance(bundle);
        }
        toContact(fansFg);

    }

    private void toContact(Fragment fg) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fg.isAdded()) {
            transaction.add(R.id.content, fg);
        } else {
            transaction.show(fg);
        }
        if (currentFg != null) {
            transaction.hide(currentFg);
        }
        transaction.commit();
        currentFg = fg;


    }


}
