package com.heckaitor.demo.contents.asyncdialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class LoadingFragment extends DialogFragment {

	public static final String TAG = LoadingFragment.class.getSimpleName();
	private DialogInterface.OnKeyListener onKeyListener;

	public static LoadingFragment newInstance() {
		Bundle args = new Bundle();
		LoadingFragment fragment = new LoadingFragment();
		fragment.setArguments(args);
		return fragment;
	}

	private ProgressDialog dialog;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new ProgressDialog(getContext());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMax(100);
		if (onKeyListener != null) {
			dialog.setOnKeyListener(onKeyListener);
		}
		return dialog;
	}

	public void updateProgress(int progress) {
		if (progress >= 0 && progress <= 100) {
			dialog.setProgress(progress);
		}
	}

	public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
		this.onKeyListener = onKeyListener;
	}
}
