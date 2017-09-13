package com.heckaitor.demo.views;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.heckaitor.demo.R;
import com.heckaitor.demo.utils.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.heckaitor.demo.utils.ViewVisibilityHelper.calculateVisiblePercents;
import static com.heckaitor.demo.utils.ViewVisibilityHelper.calculateVisiblePercentsWithTopOffset;
import static com.heckaitor.demo.utils.ViewVisibilityHelper.isHiddenBottom;
import static com.heckaitor.demo.utils.ViewVisibilityHelper.isHiddenLeft;
import static com.heckaitor.demo.utils.ViewVisibilityHelper.isHiddenRight;
import static com.heckaitor.demo.utils.ViewVisibilityHelper.isHiddenTop;

/**
 * view坐标系
 */
public class ViewCoordinateActivity extends AppCompatActivity {
    
    @BindView(R.id.textView) TextView mView;

    private int mX, mY;
    
    private float mVisiblePercents;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coord);
        ButterKnife.bind(this);
        
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        final int offsetX = (int) (mX - event.getRawX());
                        final int offsetY = (int) (mY - event.getRawY());
                        ((View) view.getParent()).scrollBy(offsetX, offsetY);
                        calVisiblePercents(view);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP: {
                        logCoordinates(view);
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
    
    private void logCoordinates(View view) {
        Logger.d(view, "------");
        Logger.v(view, "getLeft", view.getLeft());
        Logger.v(view, "getTop", view.getTop());
        Logger.v(view, "getRight", view.getRight());
        Logger.v(view, "getBottom", view.getBottom());
    
        Logger.v(view, "getX", view.getX());
        Logger.v(view, "getY", view.getY());
        
        Logger.v(view, "getScrollX", view.getScrollX());
        Logger.v(view, "getScrollY", view.getScrollY());
        
        int[] location = new int[2];
        view.getLocationInWindow(location);
        Logger.v(view, "getLocationInWindow", "(" + location[0] + ", " + location[1] + ")");
        
        view.getLocationOnScreen(location);
        Logger.v(view, "getLocationOnScreen", "(" + location[0] + ", " + location[1] + ")");
    
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Logger.v(view, "getGlobalVisibleRect", rect.toShortString());
        
        view.getLocalVisibleRect(rect);
        Logger.v(view, "getLocalVisibleRect", rect.toShortString());

    }
    
    private void calVisiblePercents(View view) {
        //final float percents = calculateVisiblePercents(view);
        final float percents = calculateVisiblePercentsWithTopOffset(view, getResources().getDimensionPixelSize(R.dimen.nav_header_height));
        if (percents != mVisiblePercents) {
            Logger.v(view, "visible percents = " + percents);
            Logger.d(view, "side hidden:", "left:" + isHiddenLeft(view)
                    + ", top:" + isHiddenTop(view)
                    + ", right:" + isHiddenRight(view)
                    + ", bottom:" + isHiddenBottom(view));
            mVisiblePercents = percents;
        }
    }
}
