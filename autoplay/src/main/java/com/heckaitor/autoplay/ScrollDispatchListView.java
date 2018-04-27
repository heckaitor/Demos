package com.heckaitor.autoplay;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 拓展原生的ListView，支持添加多个OnScrollListener
 * Created by kaige1 on 2018/3/26.
 */
public class ScrollDispatchListView extends ListView {

    private OnScrollListenerHolder mOnScrollListener;

    public ScrollDispatchListView(Context context) {
        this(context, null);
    }

    public ScrollDispatchListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDispatchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        // 原来的单个设置改为添加
        addOnScrollListener(listener);
    }

    /**
     * 添加滑动listener
     * @param listener
     */
    public void addOnScrollListener(OnScrollListener listener) {
        if (listener == null) {
            return;
        }

        if (mOnScrollListener == null) {
            mOnScrollListener = new OnScrollListenerHolder();
            super.setOnScrollListener(mOnScrollListener);
        }
        mOnScrollListener.mListeners.add(listener);
    }

    /**
     * 移除滑动listener
     * @param listener
     */
    public void removeOnScrollListener(OnScrollListener listener) {
        mOnScrollListener.mListeners.remove(listener);
    }

    private static class OnScrollListenerHolder implements OnScrollListener {

        List<OnScrollListener> mListeners = new ArrayList<>(2);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            for (OnScrollListener listener : mListeners) {
                if (listener != null) {
                    listener.onScrollStateChanged(view, scrollState);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mListeners != null) {
                for (OnScrollListener listener : mListeners) {
                    if (listener != null) {
                        listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                    }
                }
            }
        }
    }
}
