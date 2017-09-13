package com.heckaitor.demo.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.heckaitor.demo.R;
import com.heckaitor.demo.utils.StringUtils;
import com.heckaitor.demo.utils.log.Logger;

import static com.heckaitor.demo.utils.ViewUtils.measureMode2String;

public class AspectRatioLayout extends FrameLayout {

    private float mRatio;

    private View mSingleChildView;

    public AspectRatioLayout(Context context) {
        this(context, null);
    }

    public AspectRatioLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AspectRatioLayout);
        mRatio = a.getFloat(R.styleable.AspectRatioLayout_ratio, 1.78f);
        a.recycle();

        mSingleChildView = new View(context);
        mSingleChildView.setBackgroundColor(Color.BLACK);
        LayoutParams params = generateDefaultLayoutParams();
        params.gravity = Gravity.CENTER;
        addViewInLayout(mSingleChildView, 0, params, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.i(this, "onMeasure");
        final float ratio = mRatio;
        if (ratio <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
            if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
                Logger.v(this, "onMeasure", "width = exactly, " + widthSize, "height = exactly, " + heightSize);
                ViewGroup.LayoutParams params = mSingleChildView.getLayoutParams();
                params.width = widthSize;
                params.height = Math.round(widthSize / ratio);
                Logger.d(this, "make child layout params", params.width + ", " + params.height);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                final int height = Math.round(widthSize / ratio);
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            }
        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
            final int width = Math.round(heightSize * ratio);
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setRatio(float ratio) {
        if (ratio > 0 && ratio != mRatio) {
            mRatio = ratio;
            invalidate();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.v(this, "onAttachedToWindow", StringUtils.commonToString(this));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.v(this, "onDetachedFromWindow", StringUtils.commonToString(this));
    }
}
