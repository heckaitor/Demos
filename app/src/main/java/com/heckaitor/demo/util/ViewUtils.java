package com.heckaitor.demo.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class ViewUtils {

    public static String measureMode2String(int mode) {
        switch (mode) {
            case View.MeasureSpec.EXACTLY: return "exactly";
            case View.MeasureSpec.AT_MOST: return "at_most";
            case View.MeasureSpec.UNSPECIFIED: return "unspecified";
            default: return "unknown";
        }
    }

    public static String scrollState2String(int state) {
        switch (state) {
            case SCROLL_STATE_TOUCH_SCROLL: return "SCROLL_STATE_TOUCH_SCROLL";
            case SCROLL_STATE_FLING: return "SCROLL_STATE_FLING";
            case SCROLL_STATE_IDLE: return "SCROLL_STATE_IDLE";
            default: return "unknown";
        }
    }

    public static String recyclerViewScrollState2String(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE: return "SCROLL_STATE_IDLE";
            case RecyclerView.SCROLL_STATE_DRAGGING: return "SCROLL_STATE_DRAGGING";
            case RecyclerView.SCROLL_STATE_SETTLING: return "SCROLL_STATE_SETTLING";
            default: return "unknown";
        }
    }

}
