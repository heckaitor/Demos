package com.heckaitor.rxandroiddemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements WeatherContract.IView {

    @BindView(R.id.textInputLayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.textView)
    TextView mResultView;

    private WeatherContract.IPresent mPresent;

    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresent = new WeatherPresent(this);
    }

    @OnClick(R.id.button)
    public void onSearch() {
        final String text = mTextInputLayout.getEditText().getText().toString();
        mPresent.searchWeather(text);
    }

    @Override
    public void showResult(String result) {
        mResultView.setText(TextUtils.isEmpty(result)? "": result);
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog = progressDialog;
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
