package com.heckaitor.demo.common;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 切换图片时，与上一张图片有一个重叠渐变的效果
 */
public class GradientImageView extends ImageView {

    private Drawable mOriginalImage;
    private Matrix mOriginalImageMatrix;

    private ValueAnimator mAnimator;
    private float mProgress;

    public GradientImageView(Context context) {
        this(context, null);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = ((Number) animation.getAnimatedValue()).floatValue();
                invalidate();
            }
        });
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (mAnimator == null) {
            super.setImageDrawable(drawable);
            return;
        }

        if (mAnimator.isRunning()) {
            return;
        }

        if (getDrawable() == null) {
            super.setImageDrawable(drawable);
            return;
        }

        mOriginalImage = getDrawable();
        mOriginalImageMatrix = new Matrix(getImageMatrix());
        super.setImageDrawable(drawable);
        mAnimator.start();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        if (mAnimator == null) {
            // xml设置图片，解析View时Animator还未创建
            super.setImageResource(resId);
            return;
        }

        if (mAnimator.isRunning()) {
            return;
        }

        if (getDrawable() == null) {
            super.setImageResource(resId);
            return;
        }

        mOriginalImage = getDrawable();
        mOriginalImageMatrix = new Matrix(getImageMatrix());
        super.setImageResource(resId);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable target = getDrawable();
        if (target != null) {
            target.setAlpha(255);
        }
        super.onDraw(canvas);

        if (mOriginalImage != null) {
            if (mProgress > 0) {
                canvas.concat(mOriginalImageMatrix);
                mOriginalImage.setAlpha((int) (mProgress * 255));
                mOriginalImage.draw(canvas);
            } else {
                mOriginalImage = null;
            }
        }
    }

}