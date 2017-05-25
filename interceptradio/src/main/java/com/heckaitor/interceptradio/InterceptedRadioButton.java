package com.heckaitor.interceptradio;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * 在普通单选按钮的基础上，增加了选项变更的拦截操作，
 * 在选项真正变更之前进行必要的处理，并决定最终是否真正进行选项变更
 *
 * Created by kaige1 on 16/9/6.
 */
public class InterceptedRadioButton extends RadioButton {

	private OnInterceptCheckedListener interceptCheckedListener;

	public InterceptedRadioButton(Context context) {
		super(context);
	}

	public InterceptedRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InterceptedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void toggle() {
		boolean consumed = false;
		if (interceptCheckedListener != null) {
			consumed = interceptCheckedListener.onCheckIntercepted();
		}

		if (!consumed) {
			super.toggle();
		}
	}

	public void setOnInterceptCheckedListener(OnInterceptCheckedListener listener) {
		this.interceptCheckedListener = listener;
	}

	public interface OnInterceptCheckedListener {

		/**
		 * 在单选项的选择状态变更之前被调用
		 * @return true 拦截此次选项变更操作，当前选择项不变；false 默认执行选择变更
		 */
		boolean onCheckIntercepted();
	}

}
