package com.heckaitor.demo.contents.asyncdialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.heckaitor.demo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * AsyncTask + ProgressDialog + show result with custom Fragment
 *
 * <p> {@link #showDialog(int)}已过时，那么显示对话框的时候自然就想到{@link android.app.DialogFragment DialogFragment}，
 * 但是不是{@link android.app.Dialog Dialog}就不能在{@link android.app.Activity Activity}里用了，必须包在{@link android.app.DialogFragment DialogFragment}中？
 * 当然不是！！！</p>
 * <p>通过一个常见的场景：异步加载数据，加载过程中显示loading状态，加载完成显示一个对话框提示，以此验证两点：</p>
 * <ol>
 *     <li>loading状态使用{@link ProgressDialog}，直接使用和{@link LoadingFragment}的区别</li>
 *     <li>加载完成后显示的对话框使用{@link AlertDialog}，使用和使用自定义Fragment的区别</li>
 * </ol>
 *
 * 通过对比，结论是：
 * <ul>
 *     <li>{@link android.app.DialogFragment DialogFragment}并不是用来取代{@link android.app.Dialog Dialog}的，只是{@link #showDialog(int)}过时而已，
 *     使用{@link android.app.Fragment Fragment}提供了更好的定制和更多的灵活性</li>
 *     <li>{@link android.app.Fragment Fragment}特别不适用于异步结果的展示，因为异步任务执行完成后并不知道当前Activity的状态，
 *     如果在{@link #onStop()}之后提交{@link FragmentTransaction FragmentTransaction}，
 *     因为{@link #onSaveInstanceState(Bundle)}已经执行过，{@link android.app.Fragment Fragment}的状态将无法保存，造成{@link IllegalStateException}：
 *     <pre>
 *         java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
 *         at android.support.v4.app.FragmentManagerImpl.checkStateLoss(FragmentManager.java:1341)
 *         at android.support.v4.app.FragmentManagerImpl.enqueueAction(FragmentManager.java:1352)
 *         at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:595)
 *         at android.support.v4.app.BackStackRecord.commit(BackStackRecord.java:574)
 *     </pre>
 *     </li>
 *     <li>虽然可以用{@link FragmentTransaction#commitAllowingStateLoss()}来忽略状态的保存，但并不是一个好的方法</li>
 *     <li>Android官方并不提倡在异步任务后使用{@link android.app.Fragment Fragment}，参考
 *     <a herf="http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html>Fragment Transactions & Activity State Loss</a>
 *     </li>
 * </ul>
 *
 * Created by heckaitor on 2016/10/17.
 */
public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = AsyncTaskActivity.class.getSimpleName();

	private LoadTask taskWithDialog;
	private LoadTask2 taskWithFragment;
	private boolean isStopped;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_dialog);
		Log.v(TAG, "onCreate: " + savedInstanceState);
		initViews();

		List<String> list = new ArrayList<>(10);
	}

	private void initViews() {
		findViewById(R.id.btnLoadWithDialog).setOnClickListener(this);
		findViewById(R.id.btnLoadWithFragment).setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "onStart: ");
		isStopped = false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(TAG, "onStop: ");
		isStopped = true;
	}
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged: " + newConfig);
    }
    
    @Override
	public void onClick(View v) {
		if (R.id.btnLoadWithDialog == v.getId()) {
			if (taskWithDialog != null && taskWithDialog.getStatus() == AsyncTask.Status.RUNNING) {
				Log.d(TAG, "taskWithDialog is running!");
				return;
			}

			taskWithDialog = new LoadTask(this);
			taskWithDialog.execute();
			Log.i(TAG, "start taskWithDialog");
		} else if (R.id.btnLoadWithFragment == v.getId()) {
			if (taskWithFragment != null && taskWithFragment.getStatus() == AsyncTask.Status.RUNNING) {
				Log.d(TAG, "taskWithFragment is running!");
				return;
			}

			taskWithFragment = new LoadTask2(this);
			taskWithFragment.execute();
			Log.i(TAG, "start taskWithFragment");
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.v(TAG, "onBackPressed: ");
	}

	/**
	 * 加载数据的异步任务，可以是AsyncTask或Loader
	 * 只用使用Dialog的写法
	 */
	private static class LoadTask extends AsyncTask<Void, Integer, String> {

		private WeakReference<AsyncTaskActivity> wrActivity;
		private ProgressDialog loadingDialog;

		public LoadTask(AsyncTaskActivity wrActivity) {
			this.wrActivity = new WeakReference<>(wrActivity);
		}

		private void showLoading(int progress) {
			if (wrActivity.get() == null) return;

			if (loadingDialog == null) {
				loadingDialog = new ProgressDialog(wrActivity.get());
				loadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				loadingDialog.setMax(100);
				loadingDialog.setCanceledOnTouchOutside(false);
				loadingDialog.setCancelable(true);
				loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							Log.d(TAG, "Dialog.onKey: " + keyCode);
							cancel(true);
							dialog.dismiss();
						}
						return true;
					}
				});
			}

			loadingDialog.setProgress(progress);
			loadingDialog.show();
		}

		private void dismissLoading() {
			if (loadingDialog != null) {
				loadingDialog.dismiss();
				loadingDialog.setOnKeyListener(null);
				loadingDialog = null;
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithDialog: onPreExecute: " + activity);
			showLoading(0);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithDialog: onProgressUpdate: " + values[0] + ", " + activity);
			if (activity != null && !activity.isFinishing()) {
				showLoading(values[0]);
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithDialog: doInBackground: " + activity);

			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(500);
					publishProgress(10 * (i + 1));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return "taskWithDialog result";
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithDialog: onCanceled: " + activity);
			if (activity != null) {
				dismissLoading();
			}
		}

		@Override
		protected void onPostExecute(final String s) {
			super.onPostExecute(s);
			dismissLoading();
			final AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithDialog: on PostExecute: " + activity);
			if (activity != null) {
				new AlertDialog.Builder(activity)
						.setMessage(s)
						.create().show();
			}
		}
	}

	/**
	 * 加载数据的异步任务，可以是AsyncTask或Loader
	 * 使用Fragment的写法
	 */
	private static class LoadTask2 extends AsyncTask<Void, Integer, String> {

		private WeakReference<AsyncTaskActivity> wrActivity;

		public LoadTask2(AsyncTaskActivity wrActivity) {
			this.wrActivity = new WeakReference<>(wrActivity);
		}

		private void showLoading(int progress) {
			AsyncTaskActivity activity = wrActivity.get();
			if (activity != null) {
				LoadingFragment loading = (LoadingFragment) activity.getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
				if (loading != null) {
					loading.updateProgress(progress);
					return;
				}

				loading = LoadingFragment.newInstance();
				loading.setOnKeyListener(new DialogInterface.OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							Log.d(TAG, "Dialog.onKey: " + keyCode);
							cancel(true);
							dialog.dismiss();
						}
						return true;
					}
				});
				loading.show(activity.getSupportFragmentManager(), LoadingFragment.TAG);
			}
		}

		private void dismissLoading() {
			AsyncTaskActivity activity = wrActivity.get();
			if (activity != null) {
				LoadingFragment loading = (LoadingFragment) activity.getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
				if (loading != null) {
					// 这里会发生IllegalStateException: con not perform this action after onSaveInstanceState
					//loading.dismiss();
					loading.dismissAllowingStateLoss();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithFragment: onPreExecute: " + activity);
			showLoading(0);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithFragment: onProgressUpdate: " + values[0] + ", " + activity);
			if (activity != null && !activity.isFinishing()) {
				showLoading(values[0]);
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithFragment: doInBackground: " + activity);

			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(500);
					publishProgress(10 * (i + 1));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return "taskWithFragment result";
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithFragment: onCanceled: " + activity);
			if (activity != null) {
				dismissLoading();
			}
		}

		@Override
		protected void onPostExecute(final String s) {
			super.onPostExecute(s);
			dismissLoading();
			final AsyncTaskActivity activity = wrActivity.get();
			Log.v(TAG, "taskWithFragment: on PostExecute: " + activity);
			if (activity != null) {
				ResultFragment fragment = (ResultFragment) activity.getSupportFragmentManager().findFragmentByTag(ResultFragment.TAG);
				if (fragment == null) {
					fragment = ResultFragment.newInstance(s);
					// 这里会发生IllegalStateException: con not perform this action after onSaveInstanceState
					// 比如加载中时点击home，因此并没有取消task，因此task执行完成后activity已经不可见了，甚至已经被回收了
					// 可以使用一个标志位来避免这个错误
					if (!activity.isStopped) {
						fragment.show(activity.getSupportFragmentManager(), ResultFragment.TAG);
					}
				}
			}
		}
	}

}
