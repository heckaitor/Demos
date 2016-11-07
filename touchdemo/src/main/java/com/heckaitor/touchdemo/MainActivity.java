package com.heckaitor.touchdemo;

import static com.heckaitor.touchdemo.TouchUtils.getTouchActionName;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.w(TAG, "dispatchTouchEvent -> " + getTouchActionName(ev));
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.w(TAG, "onTouchEvent -> " + getTouchActionName(event));
		return super.onTouchEvent(event);
	}
}
