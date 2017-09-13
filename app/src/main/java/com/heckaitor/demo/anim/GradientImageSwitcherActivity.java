package com.heckaitor.demo.anim;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.heckaitor.demo.R;
import com.heckaitor.demo.common.GradientImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GradientImageSwitcherActivity extends AppCompatActivity {

    @BindView(R.id.giv_image)
    GradientImageView mView;

    private boolean isRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_image_switcher);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_toggle)
    public void toggle(View view) {
        if (isRed) {
            mView.setImageResource(R.drawable.bg_big_thin);
            isRed = false;
        } else {
            mView.setImageResource(R.drawable.bg_small_fat);
            isRed = true;
        }
    }
}