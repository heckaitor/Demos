package com.heckaitor.demo.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * View可见性的工具类
 * Created by kaige1 on 28/07/2017.
 */
public class ViewVisibilityHelper {
    
    private static Rect mVideoVisibleRect;
    
    /**
     * 计算当前View的可见区域与View大小的占比
     * @param view
     * @return 0～1
     */
    public static float calculateVisibilityPercents(@NonNull View view) {
        float percents = 1;
        if (mVideoVisibleRect == null) {
            mVideoVisibleRect = new Rect();
        }
        
        view.getLocalVisibleRect(mVideoVisibleRect);
        
        final int viewWidth = view.getWidth();
        final int viewHeight = view.getHeight();
        final int visibleWidth = mVideoVisibleRect.width();
        final int visibleHeight = mVideoVisibleRect.height();
        if (viewWidth > 0 && viewHeight > 0) {
            percents = (float) visibleWidth * visibleHeight / (viewWidth * viewHeight);
        }
    
        //Logger.v(view, "getVideoVisiblePercent", String.valueOf(percents));
        return percents;
    }
    
    /**
     * 判断View的底部是否被遮挡
     * @param view
     * @return
     */
    public static boolean isPartiallyHiddenBottom(View view) {
        if (mVideoVisibleRect == null) {
            mVideoVisibleRect = new Rect();
        }
        view.getLocalVisibleRect(mVideoVisibleRect);
        return mVideoVisibleRect.bottom > 0 && mVideoVisibleRect.bottom < view.getHeight();
    }
    
    /**
     * 判断View的顶部是否被遮挡
     * @param view
     * @return
     */
    public static boolean isPartiallyHiddenTop(View view) {
        if (mVideoVisibleRect == null) {
            mVideoVisibleRect = new Rect();
        }
        view.getLocalVisibleRect(mVideoVisibleRect);
        return mVideoVisibleRect.top > 0;
    }
    
    /**
     * 判断View的左侧是否被遮挡
     * @param view
     * @return
     */
    public static boolean isPartiallyHiddenLeft(View view) {
        if (mVideoVisibleRect == null) {
            mVideoVisibleRect = new Rect();
        }
        view.getLocalVisibleRect(mVideoVisibleRect);
        return mVideoVisibleRect.left > 0;
    }
    
    /**
     * 判断View的右侧是否被遮挡
     * @param view
     * @return
     */
    public static boolean isPartiallyHiddenRight(View view) {
        if (mVideoVisibleRect == null) {
            mVideoVisibleRect = new Rect();
        }
        view.getLocalVisibleRect(mVideoVisibleRect);
        return mVideoVisibleRect.right > 0 && mVideoVisibleRect.right < view.getWidth();
    }
}
