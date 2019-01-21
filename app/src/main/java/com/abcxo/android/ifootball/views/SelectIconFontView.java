package com.abcxo.android.ifootball.views;

import android.content.Context;
import android.util.AttributeSet;

public class SelectIconFontView extends IconFontView{
    public SelectIconFontView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SelectIconFontView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectIconFontView(Context context) {
        super(context);
    }


    private int text;
    private int selectText;
    public void setText(int text,int selectText){
        this.text = text;
        this.selectText = selectText;
        setText(text);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selectText>0){
            if (selected){
                setText(selectText);
            }else {
                setText(text);
            }
        }

    }

}
