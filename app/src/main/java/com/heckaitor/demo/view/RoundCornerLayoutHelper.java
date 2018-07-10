package com.heckaitor.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.heckaitor.demo.R;

/**
 * 圆角ViewGroup工具类。使用方法:
 *
 * <li>在ViewGroup的构造方法中，初始化helper</li>
 * <li>重写ViewGroup.onSizeChanged，调用helper的方法传入宽高</li>
 * <li>重写ViewGroup.dispatchDraw：</li>
 * <pre>
 *     @Override
 *     protected void dispatchDraw(Canvas canvas) {
 *         canvas.saveLayer(0, 0, getWidth(), getHeight(), null, ALL_SAVE_FLAG);
 *         super.dispatchDraw(canvas);
 *         mHelper.onClipDraw(canvas);
 *         canvas.restore();
 *     }
 * </pre>
 * <li>布局文件中，使用app:radius="xxx"定义圆角半径</li>
 *
 * Created by kaige1 on 2018/7/4.
 */
public class RoundCornerLayoutHelper {

    private Paint mPaint;

    private int mRadius;
    private RectF mArea = new RectF();
    private Path mPath = new Path();

    public void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerLayout);
        mRadius = array.getDimensionPixelSize(R.styleable.RoundCornerLayout_radius, 0);
        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void onSizeChanged(View view, int w, int h) {
        mArea.set(view.getPaddingLeft(), view.getPaddingTop(), w - view.getPaddingRight(), h - view.getPaddingBottom());
        mPath.addRoundRect(mArea, mRadius, mRadius, Path.Direction.CW);
    }

    public void onClipDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
