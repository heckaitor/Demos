package com.heckaitor.demo.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.heckaitor.demo.R;

/**
 * 带倒计时的播放按钮
 * Created by heckaitor on 13/10/2017.
 */
public class CountDownPlayButton extends View {

    public interface OnCountDownListener {
        /**
         * 倒计时开始
         */
        void onStart();

        /**
         * 倒计时正常结束
         */
        void onFinish();

        /**
         * 倒计时未结束时，用户直接点击
         */
        void onUserClicked();

        /**
         * 倒计时被取消
         */
        void onCanceled();
    }

    private float mProgress = 0; //当前进度[0, 1]
    private int mDuration; //倒计时时长

    //style
    private int mProgressBarColor;
    private int mProgressBarWidth;
    private int mOffset;

    private Paint mPaint;
    private RectF mRect;
    private ValueAnimator mAnimator;

    private boolean mRunning;

    private OnCountDownListener mOnCountDownListener;

    public CountDownPlayButton(Context context) {
        this(context, null);
    }

    public CountDownPlayButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownPlayButton);
        mProgressBarColor = a.getColor(R.styleable.CountDownPlayButton_progressColor, Color.RED);
        mProgressBarWidth = a.getDimensionPixelSize(R.styleable.CountDownPlayButton_progressWidth, 5);
        mDuration = a.getInt(R.styleable.CountDownPlayButton_duration, 3000);
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mProgressBarColor);
        mPaint.setStrokeWidth(mProgressBarWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect = new RectF();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProgress != 0 && mOnCountDownListener != null) {
                    reset();
                    mOnCountDownListener.onUserClicked();
                }
            }
        });

        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = ((Number) animation.getAnimatedValue()).floatValue();
                invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mRunning) {
                    mRunning = false;
                    if (mOnCountDownListener != null) {
                        mOnCountDownListener.onFinish();
                    }
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 调整为正方形
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        mOffset = (int) (5f / 64 * getMeasuredWidth() + 0.5f) ;
        mOffset -= mProgressBarWidth >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRect.set(mOffset, mOffset, getWidth() - mOffset, getHeight() - mOffset);
        canvas.drawArc(mRect, -90, 270 * mProgress, false, mPaint);
    }

    /**
     * 开始倒计时
     */
    public void start() {
        reset();

        mRunning = true;
        mAnimator.start();
        if (mOnCountDownListener != null) {
            mOnCountDownListener.onStart();
        }
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        reset();
        if (mOnCountDownListener != null) {
            mOnCountDownListener.onCanceled();
        }
    }

    /**
     * 重置
     */
    public void reset() {
        mProgress = 0;
        mRunning = false;
        invalidate();

        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    public void setOnCountDownListener(OnCountDownListener listener) {
        this.mOnCountDownListener = listener;
    }
}
