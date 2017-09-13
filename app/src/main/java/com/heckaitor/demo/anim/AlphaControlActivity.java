package com.heckaitor.demo.anim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlphaControlActivity extends AppCompatActivity {

    private static final float LIGHT_ALPHA = 1f;
    private static final float DIM_ALPHA   = 0.1f;

    @BindView(R.id.iv_target)
    View mTargetView;

    private boolean mLighting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_control);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_light)
    public void lightOn(View view) {
        if (!mLighting) {
            alphaView(mTargetView, LIGHT_ALPHA);
            mLighting = true;
        }
    }

    @OnClick(R.id.btn_dim)
    public void dim(View view) {
        if (mLighting) {
            alphaView(mTargetView, DIM_ALPHA);
            mLighting = false;
        }
    }

    private void alphaView(View view, float alpha) {
        view.animate().alpha(alpha).setDuration(200).start();
    }

}
