package com.xinghua.customviewlibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.xinghua.customviewlibrary.R;

public class RatioImageView extends AppCompatImageView {

    private float ratio = 1;

    public RatioImageView(Context mContext) {
        this(mContext, null, 0);
    }
    public RatioImageView(Context mContext, AttributeSet attrs) {
        this(mContext, attrs, 0);
    }

    public RatioImageView(Context mContext, AttributeSet attrs, int defStyle) {
        super(mContext, attrs, defStyle);
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        ratio = a.getFloat(R.styleable.RatioImageView_ratio, 1);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        int h = (int) (w * ratio);
        setMeasuredDimension(w + getPaddingLeft() + getPaddingRight(), h + getPaddingTop() + getPaddingBottom());
    }
}
