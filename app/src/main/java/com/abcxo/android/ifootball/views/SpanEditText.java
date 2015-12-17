package com.abcxo.android.ifootball.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.abcxo.android.ifootball.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * StrongHope
 * Created by gongyasen on 2015/6/18.a
 */
public class SpanEditText extends EditText {
    public SpanEditText(Context context) {
        super(context);
    }

    public SpanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpanEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


//    @Override
//    protected void onSelectionChanged(int selStart, int selEnd) {
//        ImageSpan[] spans = getText().getSpans(0, getText().length(), ImageSpan.class);
//        for (ImageSpan myImageSpan : spans) {
//            if (getText().getSpanEnd(myImageSpan) - 1 == selStart) {
//                selStart = selStart + 1;
//                setSelection(selStart);
//                break;
//            }
//        }
//        super.onSelectionChanged(selStart, selEnd);
//    }

    /**
     * 校验当前输入Span的合法性,覆写这条用来实现自己的校验
     *
     * @return
     */
    public boolean checkInputSpan(String showText, String returnText) {
        return true;
    }

    /**
     * 添加一个Span
     */
    public void addSpan(String showText, String returnText) {
        if (!checkInputSpan(showText, returnText)) {
            return;
        }
        getText().append(showText);
        SpannableString spannableString = new SpannableString(getText());
        generateOneSpan(spannableString, new UnSpanText(spannableString.length() - showText.length(), spannableString.length(), showText, returnText));
        setText(spannableString);
        setSelection(spannableString.length());
    }

    /**
     * 获得所有的returnText列表
     */
    public List<String> getAllReturnStringList() {
        ImageSpan[] spans = getText().getSpans(0, getText().length(), ImageSpan.class);
        List<String> list = new ArrayList<String>();
        for (ImageSpan myImageSpan : spans) {
            list.add(myImageSpan.returnText);
        }
        List<UnSpanText> texts = getAllTexts(spans, getText());
        for (UnSpanText unSpanText : texts) {
            list.add(unSpanText.returnText.toString());
        }
        return list;
    }


    public List<String> getSpanStringList() {
        ImageSpan[] spans = getText().getSpans(0, getText().length(), ImageSpan.class);
        List<String> list = new ArrayList<String>();
        for (ImageSpan myImageSpan : spans) {
            list.add(myImageSpan.returnText);
        }
        return list;
    }

    /**
     * 生成一个Span
     *
     * @param spannableString
     * @param unSpanText
     */
    private void generateOneSpan(Spannable spannableString, UnSpanText unSpanText) {
        View spanView = getSpanView(getContext(), unSpanText.showText.toString(), getMeasuredWidth());
        BitmapDrawable bitmapDrawable = (BitmapDrawable) UIUtils.convertViewToDrawable(spanView);
        bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
        ImageSpan what = new ImageSpan(bitmapDrawable, unSpanText.showText.toString(), unSpanText.returnText);
        int start = unSpanText.start;
        int end = unSpanText.end;
        spannableString.setSpan(what, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 得到所有非Span的文本集合
     *
     * @param spans
     * @return
     */
    private List<UnSpanText> getAllTexts(ImageSpan[] spans, Editable edittext) {
        List<UnSpanText> texts = new ArrayList<UnSpanText>();
        int start;
        int end;
        CharSequence text;

        List<Integer> sortStartEnds = new ArrayList<Integer>();
        sortStartEnds.add(0);
        for (ImageSpan myImageSpan : spans) {
            sortStartEnds.add(edittext.getSpanStart(myImageSpan));
            sortStartEnds.add(edittext.getSpanEnd(myImageSpan));
        }
        sortStartEnds.add(edittext.length());
        Collections.sort(sortStartEnds);

        for (int i = 0; i < sortStartEnds.size(); i = i + 2) {
            start = sortStartEnds.get(i);
            end = sortStartEnds.get(i + 1);
            text = edittext.subSequence(start, end);
            if (!TextUtils.isEmpty(text)) {
                texts.add(new UnSpanText(start, end, text, text.toString()));
            }
        }

        return texts;
    }


    /**
     * 获得span视图
     *
     * @param context
     * @return
     */
    public View getSpanView(Context context, String text, int maxWidth) {
        TextView view = new TextView(context);
        view.setMaxWidth(maxWidth);
        view.setText(text);
        view.setSingleLine(true);
        view.setTextSize(getTextSize());
        view.setTextColor(getContext().getResources().getColor(R.color.color_button_accent));
        return view;
    }


    /**
     * 我的ImageSpan
     */
    private class ImageSpan extends android.text.style.ImageSpan {
        public String showText;
        public String returnText;

        public ImageSpan(Drawable d, String showText, String returnText) {
            super(d);
            this.showText = showText;
            this.returnText = returnText;
        }

    }

    /**
     * 表示一段非SPan字符串
     */
    private class UnSpanText {
        public int start;
        public int end;
        public CharSequence showText;
        public String returnText;

        public UnSpanText(int start, int end, CharSequence showText, String returnText) {
            this.start = start;
            this.end = end;
            this.showText = showText;
            this.returnText = returnText;
        }
    }

}
