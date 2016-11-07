package com.heckaitor.touchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.heckaitor.touchdemo.TouchUtils.getTouchActionName;

/**
 * Created by kaige1 on 2016/11/7.
 */
public class TouchViewChild extends View {

	private static final String TAG = "child";

	public TouchViewChild(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchViewChild(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.d(TAG, "dispatchTouchEvent -> " + getTouchActionName(event));
		return super.dispatchTouchEvent(event);
//		return true;
//		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent -> " + getTouchActionName(event));
		return super.onTouchEvent(event);
//		return true;
//		return false;
	}
}
