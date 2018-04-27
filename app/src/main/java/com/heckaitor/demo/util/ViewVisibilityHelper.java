package com.heckaitor.demo.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

import com.heckaitor.utils.log.Logger;

/**
 * View可见性的工具类
 * Created by kaige1 on 28/07/2017.
 */
public class ViewVisibilityHelper {

    private static Rect mVisibleRect;

    /**
     * 计算当前View的可见区域与View大小的占比
     * @param view
     * @return 0～1
     */
    public static float calculateVisiblePercents(@NonNull View view) {
        float percents = 0;
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }

        // 注意返回值的坑：true表示View有部分或全部可见，false表示完全不可见
        final boolean visible = view.getLocalVisibleRect(mVisibleRect);
        if (visible) {
            final int viewWidth = view.getWidth();
            final int viewHeight = view.getHeight();
            final int visibleWidth = mVisibleRect.width();
            final int visibleHeight = mVisibleRect.height();
            if (viewWidth > 0 && viewHeight > 0) {
                percents = (float) visibleWidth * visibleHeight / (viewWidth * viewHeight);
            }
        }
        Logger.v(view, "getVideoVisiblePercent ", visible + "  " + percents);
        return percents;
    }

    /**
     * 计算View的可见性
     * @param view
     * @param topOffset 从屏幕顶部开始的一段距离：如果View落在其中，虽然仍然可见，但计算时需要去掉重合的区域。
     *                  适用于透明状态栏的情况
     * @return
     */
    public static float calculateVisiblePercentsWithTopOffset(@NonNull View view, int topOffset) {
        float percents = 0;
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }

        final boolean visible = view.getGlobalVisibleRect(mVisibleRect);
        if (visible) {
            final int viewWidth = view.getWidth();
            final int viewHeight = view.getHeight();
            final int visibleWidth = mVisibleRect.width();
            final int visibleHeight = Math.max(mVisibleRect.bottom - Math.max(mVisibleRect.top, topOffset), 0);
            if (viewWidth > 0 && viewHeight > 0) {
                percents = (float) visibleWidth * visibleHeight / (viewWidth * viewHeight);
            }
        }
        Logger.v(view, "getVideoVisiblePercent ", visible + "  " + percents);
        return percents;
    }

    /**
     * 判断View的底部是否被遮挡
     * @param view
     * @return
     */
    public static boolean isHiddenBottom(View view) {
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }
        final boolean visible = view.getLocalVisibleRect(mVisibleRect);
        return !visible ||
                (mVisibleRect.bottom > 0 && mVisibleRect.bottom < view.getHeight());
    }

    /**
     * 判断View的顶部是否被遮挡
     * @param view
     * @return
     */
    public static boolean isHiddenTop(View view) {
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }
        final boolean visible = view.getLocalVisibleRect(mVisibleRect);
        return !visible || mVisibleRect.top > 0;
    }

    /**
     * 判断View的左侧是否被遮挡
     * @param view
     * @return
     */
    public static boolean isHiddenLeft(View view) {
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }
        final boolean visible = view.getLocalVisibleRect(mVisibleRect);
        return !visible || mVisibleRect.left > 0;
    }

    /**
     * 判断View的右侧是否被遮挡
     * @param view
     * @return
     */
    public static boolean isHiddenRight(View view) {
        if (mVisibleRect == null) {
            mVisibleRect = new Rect();
        }
        final boolean visible = view.getLocalVisibleRect(mVisibleRect);
        return !visible ||
                (mVisibleRect.right > 0 && mVisibleRect.right < view.getWidth());
    }
}
