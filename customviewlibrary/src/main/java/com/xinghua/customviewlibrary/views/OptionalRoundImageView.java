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

public class OptionalRoundImageView extends AppCompatImageView {
    private float mRadius = 0;
    private float mLeftTopRadius = 0;
    private float mRightTopRadius = 0;
    private float mRightBottomRadius = 0;
    private float mLeftBottomRadius = 0;
    private Shape mShape;
    private Paint mPaint;
    public OptionalRoundImageView(Context mContext) {
        this(mContext, null, 0);
    }
    public OptionalRoundImageView(Context mContext, AttributeSet attrs) {
        this(mContext, attrs, 0);
    }

    public OptionalRoundImageView(Context mContext, AttributeSet attrs, int defStyle) {
        super(mContext, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.OptionalRoundImageView);
        mRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_radius, 0);
        mLeftTopRadius = a.getDimensionPixelSize(R.styleable.OptionalRoundImageView_left_top_radius, 0);
        mLeftBottomRadius = a.getDimensionPixelSize(R.styleable.OptionalRoundImageView_left_bottom_radius, 0);
        mRightTopRadius = a.getDimensionPixelSize(R.styleable.OptionalRoundImageView_right_top_radius, 0);
        mRightBottomRadius = a.getDimensionPixelSize(R.styleable.OptionalRoundImageView_right_bottom_radius, 0);
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
                if (mLeftBottomRadius == 0 && mRightBottomRadius == 0
                        && mRightTopRadius == 0 && mLeftTopRadius == 0) {
                    Arrays.fill(innerRadius, mRadius);
                } else {
                    Arrays.fill(innerRadius, 0, 2, mLeftTopRadius);
                    Arrays.fill(innerRadius, 2, 4, mRightTopRadius);
                    Arrays.fill(innerRadius, 4, 6, mRightBottomRadius);
                    Arrays.fill(innerRadius, 6, 8, mLeftBottomRadius);
                }
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
