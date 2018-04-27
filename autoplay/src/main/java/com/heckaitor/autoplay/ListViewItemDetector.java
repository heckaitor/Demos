package com.heckaitor.autoplay;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.heckaitor.utils.StringUtils;
import com.heckaitor.utils.log.Logger;

import java.lang.reflect.Field;

/**
 * 针对{@link ListView}进行适配
 */
public class ListViewItemDetector extends ExclusiveItemDetector<ListView> {

    private ScrollDetectListener mListenerProxy;

    public ListViewItemDetector(ListView listView, int topOffset, int bottomOffset) {
        super(listView, topOffset, bottomOffset);
        setUpScrollListener();
    }

    /**
     * 对TargetView设置滑动监听
     */
    private void setUpScrollListener() {
        final ListView view = mTargetView;
        if (view != null) {
            if (view instanceof ScrollDispatchListView) {
                // ScrollDispatchListView支持添加多个listener，直接添加
                mListenerProxy = new ScrollDetectListener(null);
                Logger.d(this, "add", StringUtils.commonToString(mListenerProxy));
                ((ScrollDispatchListView) view).addOnScrollListener(mListenerProxy);
            } else {
                final AbsListView.OnScrollListener listener = hookListViewScrollListener(view);
                if (listener != null) {
                    mListenerProxy = new ScrollDetectListener(listener);
                    Logger.d(this, "hook", StringUtils.commonToString(listener), StringUtils.commonToString(mListenerProxy));
                    view.setOnScrollListener(mListenerProxy); //setListener会触发一次onScroll探测
                }
            }
        }
    }

    /**
     * 注意：由于代理ListView的OnScrollListener，因此业务方需要在启动自动探测之前设置OnScrollListener，
     * 否则自动探测会失效
     * @param view
     * @return
     */
    private AbsListView.OnScrollListener hookListViewScrollListener(ListView view) {
        if (view == null) {
            return null;
        }

        try {
            Field field = AbsListView.class.getDeclaredField("mOnScrollListener");
            field.setAccessible(true);
            return (AbsListView.OnScrollListener) field.get(view);
        } catch (NoSuchFieldException e) {
            Logger.e(this, e);
            return null;
        } catch (IllegalAccessException e) {
            Logger.e(this, e);
            return null;
        }
    }

    @Override
    public void start() {
        mEnable = true;
        if (mListenerProxy == null) {
            setUpScrollListener();
        } else {
            final ListView view = mTargetView;
            detectItem(view);
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (mListenerProxy == null) {
            setUpScrollListener();
        }
    }

    @Override
    public void stop() {
        if (mListenerProxy != null) {
            final ListView view = mTargetView;
            if (view instanceof ScrollDispatchListView) {
                ((ScrollDispatchListView) view).removeOnScrollListener(mListenerProxy);
            } else {
                // 恢复原始的滑动监听
                final AbsListView.OnScrollListener originalListener = mListenerProxy.object;
                if (view != null) {
                    view.setOnScrollListener(originalListener);
                }
            }
            mListenerProxy = null;
        }
        super.stop();
    }

    @Override
    protected int getChildCount(ListView listView) {
        return listView.getChildCount();
    }

    @Override
    protected int indexOfChild(ListView listView, View view) {
        return listView.indexOfChild(view);
    }

    @Override
    protected View getChildAt(ListView listView, int index) {
        return listView.getChildAt(index);
    }

    @Override
    protected int getFirstVisibleIndex(ListView listView) {
        return listView.getFirstVisiblePosition();
    }

    private class ScrollDetectListener implements AbsListView.OnScrollListener {

        private AbsListView.OnScrollListener object;

        public ScrollDetectListener(AbsListView.OnScrollListener object) {
            this.object = object;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (object != null) {
                object.onScrollStateChanged(view, scrollState);
            }

            // 滑动停止时检测
            // if (scrollState == SCROLL_STATE_IDLE) {
            //     detectOnScroll((ListView) view, marks);
            // }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (object != null) {
                object.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            // 滑动检测
            detectItem((ListView) view);
        }
    }
}
