package com.heckaitor.demo.autoplay;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heckaitor.autoplay.BusProvider;
import com.heckaitor.autoplay.DetectableItem;
import com.heckaitor.demo.R;
import com.heckaitor.demo.view.AspectRatioLayout;

public class LightItemView extends AspectRatioLayout implements DetectableItem {

    private ImageView mImageView;
    private TextView mTextView;

    public LightItemView(Context context) {
        this(context, null);
    }

    public LightItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_simple_imge_text, this);
        mImageView = findViewById(R.id.iv_image);
        mTextView = findViewById(R.id.tv_text);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BusProvider.getInstance().unregister(this);
    }

    public void setImageResource(@DrawableRes int resId) {
        mImageView.setImageResource(resId);
    }

    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    @Override
    public View getDetectedView() {
        return this;
    }

    @Override
    public void activate() {
        setBackgroundColor(Color.DKGRAY);
    }

    @Override
    public void deactivate() {
        setBackgroundColor(Color.LTGRAY);
    }
}
