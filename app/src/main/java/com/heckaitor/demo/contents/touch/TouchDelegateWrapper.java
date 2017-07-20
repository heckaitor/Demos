package com.heckaitor.demo.contents.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

import com.heckaitor.demo.utils.MotionEventUtils;
import com.heckaitor.demo.utils.log.Logger;

public class TouchDelegateWrapper {
    
    final TouchDelegate instance;
    final Rect rect;
    final View view;
    
    public TouchDelegateWrapper(Rect rect, View delegateView) {
        this.rect = rect;
        this.view = delegateView;
        this.instance = new TouchDelegate(rect, delegateView)  {
    
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                Logger.w(this, "onTouchEvent", MotionEventUtils.action2String(event.getAction()));
                return super.onTouchEvent(event);
                //return true;
            }
        };
    }
}
