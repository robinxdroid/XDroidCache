package com.xdroid.cache.example.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SquareButton extends TextView {
    public SquareButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButton(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}