package com.xinghua.customviewlibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.xinghua.customviewlibrary.R;

import java.util.Arrays;

public class RoundImageView extends AppCompatImageView {
    private float mRadius = 0;
    private Shape mShape;
    private Paint mPaint;
    public RoundImageView(Context mContext) {
        this(mContext, null, 0);
    }
    public RoundImageView(Context mContext, AttributeSet attrs) {
        this(mContext, attrs, 0);
    }

    public RoundImageView(Context mContext, AttributeSet attrs, int defStyle) {
        super(mContext, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_radius, 0);
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (mShape == null) {
                float[] outRadius = new float[8];
                Arrays.fill(outRadius, 0);
                //fix a bug for paddingLeft or paddingTop is 0, the image wouldn't show.
                int paddingLeft = getPaddingLeft() <= 0 ? 1 : getPaddingLeft();
                int paddingRight = getPaddingRight() <= 0 ? 1 : getPaddingRight();
                int paddingTop = getPaddingTop() <= 0 ? 1 : getPaddingTop();
                int paddingBottom = getPaddingBottom() <= 0 ? 1 : getPaddingBottom();
                RectF rectF = new RectF(paddingLeft, paddingTop, paddingRight, paddingBottom);
                float[] innerRadius = new float[8];
                Arrays.fill(innerRadius, mRadius);

                mShape = new RoundRectShape(outRadius, rectF, innerRadius);
            }
            mShape.resize(getWidth(), getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.save();
        super.onDraw(canvas);
        if (mShape != null) {
            mShape.draw(canvas, mPaint);
        }
        canvas.restoreToCount(saveCount);
    }
}
