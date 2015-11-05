package com.abcxo.android.ifootball.controllers.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by shadow on 15/11/5.
 */
public class SpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
    private ThemedSpinnerAdapter.Helper dropDownHelper;

    public SpinnerAdapter(Context context, String[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        dropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = dropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return dropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        dropDownHelper.setDropDownViewTheme(theme);
    }
}
