package com.heckaitor.demo.bezier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.heckaitor.bezier.BezierView;
import com.heckaitor.demo.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class BezierActivity extends AppCompatActivity {

	@BindView(R.id.bezierView)
	BezierView bezierView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bezier);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.button)
	public void plot(View view) {
		final int length = 10;
		final float[] values = new float[length];
		final Random random = new Random();
		for (int i = 0; i < length; i++) {
			values[i] = random.nextFloat() * 1000;
		}

		bezierView.setValues(values);
	}

	@OnCheckedChanged(R.id.checkDrawValue)
	public void onDrawValueChanged(CompoundButton buttonView, boolean isChecked) {
		bezierView.setDrawValuePoints(isChecked);
		bezierView.invalidate();
	}

	@OnCheckedChanged(R.id.checkDrawControl)
	public void onDrawControlChanged(CompoundButton buttonView, boolean isChecked) {
		bezierView.setDrawControlPoints(isChecked);
		bezierView.invalidate();
	}
}
