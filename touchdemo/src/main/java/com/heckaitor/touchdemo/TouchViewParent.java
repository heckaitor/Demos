package com.heckaitor.touchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import static com.heckaitor.touchdemo.TouchUtils.getTouchActionName;

/**
 * Created by kaige1 on 2016/11/7.
 */
public class TouchViewParent extends FrameLayout {

	private static final String TAG = "Parent";

	public TouchViewParent(Context context) {
		super(context);
	}

	public TouchViewParent(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.v(TAG, "dispatchTouchEvent -> " + getTouchActionName(ev));
		return super.dispatchTouchEvent(ev);
//		return true;
//		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.v(TAG, "onInterceptTouchEvent -> " + getTouchActionName(ev));
//		return super.onInterceptTouchEvent(ev);
//		return true;
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.v(TAG, "onTouchEvent -> " + getTouchActionName(event));
//		return super.onTouchEvent(event);
		return true;
	}
}
