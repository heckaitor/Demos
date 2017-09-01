package com.heckaitor.demo.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.heckaitor.demo.R;
import com.heckaitor.demo.utils.log.Logger;

import static com.heckaitor.demo.utils.ViewUtils.measureMode2String;

public class AspectRatioLayout extends LinearLayout {

    private float mRatio;

    public AspectRatioLayout(Context context) {
        this(context, null);
    }

    public AspectRatioLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AspectRatioLayout);
        mRatio = a.getFloat(R.styleable.AspectRatioLayout_ratio, 1.78f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final float ratio = mRatio;
        if (ratio <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Logger.v(this, "onMeasure",
                "width: " + measureMode2String(widthMode) + ", " + widthSize,
                "height: " + measureMode2String(heightMode) + ", " + heightSize);

        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
            if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                final int height = (int) (widthSize / ratio);
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            }
        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
            final int width = (int) (heightSize * ratio);
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

}
