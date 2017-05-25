package com.heckaitor.demo.views.interceptradio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class InterceptDialog extends DialogFragment {

	public static final String TAG = "confirm";

	private static final String ARG_TEXT = "text";

	private OnConfirmListener onConfirmListener;

	public static InterceptDialog newInstance(String text) {
		InterceptDialog fragment = new InterceptDialog();
		Bundle args = new Bundle();
		args.putString(ARG_TEXT, text);
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String text = getArguments().getString(ARG_TEXT);
		return new AlertDialog.Builder(getContext())
				.setTitle("Confirm??")
				.setMessage(text)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (onConfirmListener != null) {
							onConfirmListener.onConfirmed(true);
						}
					}
				})
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (onConfirmListener != null) {
							onConfirmListener.onConfirmed(false);
						}
					}
				})
				.create();
	}

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public interface OnConfirmListener {
		void onConfirmed(boolean approved);
	}
}
