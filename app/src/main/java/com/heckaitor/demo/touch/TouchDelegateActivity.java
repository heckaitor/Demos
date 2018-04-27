package com.heckaitor.demo.touch;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.heckaitor.demo.R;
import com.heckaitor.demo.util.MotionEventUtils;
import com.heckaitor.utils.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View touch事件demo
 */
public class TouchDelegateActivity extends AppCompatActivity {
    
    @BindView(R.id.cl_outer) CustomLayout mOuterLayout;
    @BindView(R.id.cl_middle) CustomLayout mMiddleLayout;
    @BindView(R.id.cv_inner_1) CustomView mInnerView1;
    @BindView(R.id.cv_inner_2) CustomView mInnerView2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_delegate);
        ButterKnife.bind(this);
        
        mOuterLayout.setTag("outer");
        mMiddleLayout.setTag("middle");
        mInnerView1.setTag("inner1");
        mInnerView2.setTag("inner2");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            final View delegateView = mInnerView1;
            final CustomLayout targetView = mOuterLayout;//mMiddleLayout
            
            int[] targetLocation = new int[2];
            targetView.getLocationInWindow(targetLocation);
            int[] delegateLocation = new int[2];
            delegateView.getLocationInWindow(delegateLocation);
            
            final int left = delegateLocation[0] - targetLocation[0];
            final int top = delegateLocation[1] - targetLocation[1];
            Rect bounds = new Rect(left, top,
                    left + delegateView.getWidth(), top + 3 * delegateView.getHeight());
            targetView.setTouchDelegate(new TouchDelegateWrapper(bounds, delegateView));
        }
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.v(this, "dispatchTouchEvent", MotionEventUtils.action2String(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(this, "onTouchEvent", MotionEventUtils.action2String(event.getAction()));
        return super.onTouchEvent(event);
    }
    
    @OnClick(R.id.cv_inner_1)
    public void onView1Clicked() {
        Toast.makeText(this, "view 1 clicked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.cv_inner_2)
    public void onView2Clicked() {
        Toast.makeText(this, "view 2 clicked!", Toast.LENGTH_SHORT).show();
    }
    
}
