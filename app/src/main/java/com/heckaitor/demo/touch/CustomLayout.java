package com.heckaitor.demo.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.heckaitor.demo.util.MotionEventUtils;
import com.heckaitor.utils.log.Logger;

public class CustomLayout extends LinearLayout {
    
    private Rect mTouchDelegateRect;
    private Paint mPaint;
    
    public CustomLayout(Context context) {
        this(context, null);
    }
    
    public CustomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.v(this, String.valueOf(getTag()), "dispatchTouchEvent", MotionEventUtils.action2String(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logger.d(this, String.valueOf(getTag()), "onInterceptTouchEvent", MotionEventUtils.action2String(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.i(this, String.valueOf(getTag()), "onTouchEvent", MotionEventUtils.action2String(event.getAction()));
        return super.onTouchEvent(event);
    }
    
    public void setTouchDelegate(@NonNull TouchDelegateWrapper delegateWrapper) {
        mTouchDelegateRect = delegateWrapper.rect;
        invalidate();
        super.setTouchDelegate(delegateWrapper.instance);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTouchDelegateRect != null) {
            canvas.save();
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            canvas.drawRect(mTouchDelegateRect, mPaint);
            canvas.restore();
        }
    }
    
}
