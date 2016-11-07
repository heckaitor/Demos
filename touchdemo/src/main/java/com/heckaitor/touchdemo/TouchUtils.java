package com.heckaitor.touchdemo;

import android.view.MotionEvent;

/**
 * Created by kaige1 on 2016/11/7.
 */
public class TouchUtils {

	public static String getTouchActionName(MotionEvent event) {
		String name = "";
		if (event != null) {
			final int action = event.getActionMasked();
			switch (action) {
				case MotionEvent.ACTION_CANCEL: name = "ACTION_CANCEL"; break;
				case MotionEvent.ACTION_DOWN: name = "ACTION_DOWN"; break;
				case MotionEvent.ACTION_MOVE: name = "ACTION_MOVE"; break;
				case MotionEvent.ACTION_UP: name = "ACTION_UP"; break;
				case MotionEvent.ACTION_OUTSIDE: name = "ACTION_OUTSIDE"; break;
				case MotionEvent.ACTION_POINTER_DOWN: name = "ACTION_POINTER_DOWN"; break;
				case MotionEvent.ACTION_POINTER_UP: name = "ACTION_POINTER_UP"; break;
				default: break;
			}
		}
		return name;
	}
}
