package com.heckaitor.demo.interceptradio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.heckaitor.demo.R;
import com.heckaitor.demo.view.InterceptedRadioButton;

/**
 * 单选框的状态变化，可以通过
 * <ul>
 *     <li>{@link RadioGroup#setOnCheckedChangeListener}</li>
 *     <li>{@link RadioButton#setOnCheckedChangeListener}</li>
 * </ul>
 *
 * 但有一些场景，在更换选项时，可能需要检查被选项是否可以被选中，比如被选中之前提示用户确认，
 * 当用户选择确认时才真正变更选项，否则仍保持原项不变。
 * <p>{@link RadioButton#setOnClickListener}并不是一个好方法，因为它总是在最后被调用，其实选项已经变更
 * {@link InterceptedRadioButton}集成自{@link RadioButton}，并增加方法{@link InterceptedRadioButton#setOnInterceptCheckedListener}
 * 让调用方有机会在选项变更之前进行处理
 * </p>
 *
 * Created by heckaitor on 16/9/6.
 */
public class InterceptRadioActivity extends AppCompatActivity {

	private static final String TAG = InterceptRadioActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intercept_radio);

		// listener的调用顺序：
		// RadioButton.OnCheckedChangeListener
		// RadioGroup.OnCheckedChangeListener
		// RadioButton.OnClickListener
		RadioGroup group = (RadioGroup) findViewById(R.id.group);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				Log.v(TAG, "group onCheckedChanged: " + i);
			}
		});

		final int count = group.getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = group.getChildAt(i);
			if (view != null && view instanceof InterceptedRadioButton) {
				final boolean intercepted = i % 2 == 0;
				setRadioListener((InterceptedRadioButton) view, intercepted);
			}
		}
	}

	/**
	 * @param button
	 * @param shouldIntercept 是否需要拦截选择
	 */
	private void setRadioListener(final InterceptedRadioButton button, final boolean shouldIntercept) {
		final String text = button.getText().toString();
		button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				Log.d(TAG, text + " onCheckedChanged: " + b);
			}
		});

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, text + " onClick");
			}
		});

		button.setOnInterceptCheckedListener(new InterceptedRadioButton.OnInterceptCheckedListener() {
			@Override
			public boolean onCheckIntercepted() {
				if (button.isChecked()) {
					return false;
				}

				Log.d(TAG, text + " onCheckIntercepted");
				if (shouldIntercept) {
					showInterceptDialog(button);
				}
				return shouldIntercept;
			}
		});
	}

	private void showInterceptDialog(final RadioButton view) {
		InterceptDialog dialog = (InterceptDialog) getSupportFragmentManager().findFragmentByTag(InterceptDialog.TAG);
		if (dialog == null) {
			dialog = InterceptDialog.newInstance(view.getText().toString());
		}

		dialog.setOnConfirmListener(new InterceptDialog.OnConfirmListener() {
			@Override
			public void onConfirmed(boolean approved) {
				if (approved) {
					view.setChecked(true);
				}
			}
		});
		dialog.show(getSupportFragmentManager(), InterceptDialog.TAG);
	}
}
