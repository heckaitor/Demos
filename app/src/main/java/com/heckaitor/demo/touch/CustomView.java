package com.heckaitor.demo.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.heckaitor.demo.util.MotionEventUtils;
import com.heckaitor.utils.log.Logger;

public class CustomView extends AppCompatTextView {
    
    public CustomView(Context context) {
        super(context);
    }
    
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.v(this, "dispatchTouchEvent", MotionEventUtils.action2String(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(this, "onTouchEvent", MotionEventUtils.action2String(event.getAction()));
        return super.onTouchEvent(event);
    }
    
}
