package com.heckaitor.demo.contents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationsActivity extends AppCompatActivity {
    
    @BindView(R.id.iv_target)
    View mTargetView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.btn_start)
    public void start() {
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1500);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setFillAfter(true);
        mTargetView.startAnimation(animation);
    }
    
    @OnClick(R.id.btn_stop)
    public void stop() {
        mTargetView.clearAnimation();
    }
    
}
