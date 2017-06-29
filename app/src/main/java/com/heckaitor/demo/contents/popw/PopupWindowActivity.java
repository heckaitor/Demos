package com.heckaitor.demo.contents.popw;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class PopupWindowActivity extends AppCompatActivity {
    
    @BindView(R.id.tv_target)
    View mTargetView;
    
    private boolean mBackgroundSettled;
    private boolean mFocusable;
    private boolean mOutsideTouchable;
    private boolean mAnchorSettled = true, mLocationSettled;
    
    private PopupWindow mPopupWindow;
    
    private int mX, mY;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        ButterKnife.bind(this);
    
        mTargetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        final int offsetX = (int) (mX - event.getRawX());
                        final int offsetY = (int) (mY - event.getRawY());
                        ((View) view.getParent()).scrollBy(offsetX, offsetY);
                        // Target随着手指移动的过程中，验证showAtLocation & showAsDropDown的区别，
                        // 后者可以跟随Target的移动而移动，内部是通过ViewTreeObserver的ScrollChange监听来实时更新位置的
                        break;
                    }
                    default: break;
                }
    
                mX = (int) event.getRawX();
                mY = (int) event.getRawY();
                
                return true;
            }
        });
    }
    
    @OnCheckedChanged(R.id.cb_set_background)
    public void onSettingPopBackground(boolean checked) {
        mBackgroundSettled = checked;
    }
    
    @OnCheckedChanged(R.id.cb_set_focus)
    public void onSettingPopFocusable(boolean checked) {
        mFocusable = checked;
    }
    
    @OnCheckedChanged(R.id.cb_set_outside_touchable)
    public void onSettingPopOutsideFocusable(boolean checked) {
        mOutsideTouchable = checked;
    }
    
    @OnCheckedChanged(R.id.rb_set_anchor)
    public void onAnchorSettled(boolean checked) {
        mAnchorSettled = checked;
    }
    
    @OnCheckedChanged(R.id.rb_set_location)
    public void onLocationSettled(boolean checked) {
        mLocationSettled = checked;
    }
    
    @OnClick(R.id.btn_show_original_pop)
    public void showOriginalPop() {
        show(new PopupWindow());
    }
    
    @OnClick(R.id.btn_show_ex_pop)
    public void showExtendPop() {
        show(new ExPopupWindow());
    }
    
    private void show(@NonNull PopupWindow ppw) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        
        final View view = LayoutInflater.from(this).inflate(R.layout.ppw_content_layout, null);
        final TextView textView = (TextView) view.findViewById(R.id.tv_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
            }
        });
        textView.setText("Pop....");
        ppw.setContentView(view);
        ppw.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        ppw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    
        // 运行以下就会发现，实际效果和api看起来的含义有多坑爹
        ppw.setBackgroundDrawable(mBackgroundSettled ? new ColorDrawable(Color.TRANSPARENT) : null);
        ppw.setFocusable(mFocusable);
        ppw.setOutsideTouchable(mOutsideTouchable);
    
        final int gravity = Gravity.LEFT | Gravity.TOP;
        if (mAnchorSettled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 第一个参数是锚点
                ppw.showAsDropDown(mTargetView, 0, 0, gravity );
            }
        }
    
        if (mLocationSettled) {
            final int[] location = new int[2];
            mTargetView.getLocationOnScreen(location);
            // 第一个参数parent，实际使用的是当前的window，所有当前界面里任意一个view的效果是等效的
            // location需要根据gravity来确定最后的位置，一般设置LEFT | TOP，此时x，y表示距离左边和上边的距离偏移
            ppw.showAtLocation(mTargetView, gravity, location[0], location[1] + mTargetView.getHeight());
        }
        
        mPopupWindow = ppw;
    }
}
