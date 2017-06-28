package com.heckaitor.demo.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.heckaitor.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewStubActivity extends AppCompatActivity {
    
    private TextView mTextView;
    private ImageView mImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.btn_show_text)
    public void showText() {
        if (mTextView == null) {
            ViewStub stub = (ViewStub) findViewById(R.id.stub_text);
            final View holderView = stub.inflate();
            mTextView = (TextView) holderView.findViewById(R.id.tv_text);
        } else {
            mTextView.setVisibility(View.VISIBLE);
        }
        
        mTextView.setText("TextView from ViewStub!");
        
        if (mImageView != null) {
            mImageView.setVisibility(View.GONE);
        }
    }
    
    @OnClick(R.id.btn_show_image)
    public void showImage() {
        if (mImageView == null) {
            ViewStub stub = (ViewStub) findViewById(R.id.stub_image);
            mImageView = (ImageView) stub.inflate();
        } else {
            mImageView.setVisibility(View.VISIBLE);
        }
        
        mImageView.setBackgroundResource(R.drawable.background);
        if (mTextView != null) {
            mTextView.setVisibility(View.GONE);
        }
    }
}
