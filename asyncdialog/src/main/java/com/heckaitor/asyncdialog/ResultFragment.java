package com.heckaitor.asyncdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ResultFragment extends DialogFragment {
	public static final String TAG = ResultFragment.class.getSimpleName();
	private static final String KEY_CONTENT = "CONTENT";

	public static ResultFragment newInstance(String content) {
		Bundle args = new Bundle();
		args.putString(KEY_CONTENT, content);
		ResultFragment fragment = new ResultFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String content = getArguments().getString(KEY_CONTENT);
		return new AlertDialog.Builder(getContext())
				.setMessage(content)
				.create();
	}
}
