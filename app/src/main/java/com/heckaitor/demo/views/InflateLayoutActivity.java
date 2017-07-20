package com.heckaitor.demo.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InflateLayoutActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MergeLayout view = new MergeLayout(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(view);
    }
    
    public static class MergeLayout extends LinearLayout {
        
        @BindView(R.id.tv_title)
        TextView mTitleView;
        
        @BindView(R.id.tv_content)
        TextView mContentView;
        
        public MergeLayout(Context context) {
            super(context);
            init();
        }
        
        public MergeLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }
        
        private void init() {
            // 自定义View在使用布局文件时，以merge作为根标签
            // inflate时传入root=this，attachToRoot=true，会将merge标签中所有的子View添加到当前View中
            // 1. 这样可以避免自定义View时，无意中多引入的一层
            // 2. 布局中对merge的属性设置不会生效，需要在代码中设置
            LayoutInflater.from(getContext()).inflate(R.layout.view_layout_merge, this, true);
            setOrientation(VERTICAL);
            setBackgroundResource(R.drawable.background);
            ButterKnife.bind(this);
        }
        
        public void setTitle(CharSequence text) {
            mTitleView.setText(TextUtils.isEmpty(text) ? "" : text);
        }
        
        public void setContent(CharSequence text) {
            mContentView.setText(TextUtils.isEmpty(text) ? "" : text);
        }
        
    }
}
