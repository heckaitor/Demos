package com.heckaitor.demo.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.heckaitor.demo.R;
import com.heckaitor.utils.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlphaControlActivity extends AppCompatActivity {

    private static final float LIGHT_ALPHA = 1f;
    private static final float DIM_ALPHA   = 0f;

    @BindView(R.id.fl_target)
    FrameLayout mTargetHolder;
    private View mTargetView;
    private boolean mLighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_control);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_light)
    public void onLightBtnClicked(View view) {
        if (!isShowing()) {
            Logger.i(this, "show");
            if (mTargetView == null) {
                mTargetView = createTargetView();
            }

            final ViewParent parent = mTargetView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                Logger.w(this, "already add, remove first");
                ((ViewGroup) parent).removeView(mTargetView);
            }
            mTargetHolder.addView(mTargetView);
            Logger.i(this, "add");
        } else {
            Logger.w(this, "already showing");
        }
    }

    @OnClick(R.id.btn_dim)
    public void onDimButtonClicked(View view) {
        if (isShowing()) {
            mTargetHolder.removeView(mTargetView);
            Logger.w(this, "remove");
        } else {
            Logger.w(this, "not showing");
        }
    }

    private boolean isShowing() {
        return mTargetView != null && mTargetView.getParent() == mTargetHolder;
    }

    private void light() {
        Logger.i(this, "show, lighting = " + mLighting);
        reset();
        if (!mLighting) {
            if (mTargetView == null) {
                mTargetView = createTargetView();
            }
            mTargetView.setAlpha(1);
            mTargetHolder.addView(mTargetView);
            Logger.d(this, "addView");
            mLighting = true;
        }
    }

    private void dim() {
        Logger.i(this, "dim, lighting = " + mLighting);
        reset();
        if (mLighting && mTargetView != null) {
            mTargetView.animate()
                    .alpha(DIM_ALPHA)
                    .setDuration(1000)
                    .setListener(mAnimatorListener)
                    .start();
        }
    }

    private void dismiss() {
        Logger.i(this, "dismiss, lighting = " + mLighting);
        reset();
        if (mLighting && mTargetView != null) {
            mTargetHolder.removeView(mTargetView);
            Logger.i(this, "removeView");
            mLighting = false;
        }
    }

    private void reset() {
        if (mTargetView != null) {
            Logger.i(this, "reset");
            mTargetView.removeCallbacks(mDismissRunnable);
            mTargetView.animate().cancel();
        }
    }

    private View createTargetView() {
        Logger.i(this, "create TargetView");
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.ic_background);
        return view;
    }

    private Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            Logger.v(animation, "onAnimationStart");
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            Logger.v(animation, "onAnimationCancel");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Logger.v(animation, "onAnimationEnd");
//            mTargetHolder.post(mDismissRunnable);
        }
    };

    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };



}
