package com.heckaitor.autoplay;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 针对{@link RecyclerView}适配
 */
public class RecyclerViewItemDetector extends ExclusiveItemDetector<RecyclerView> {

    private RecyclerView.OnScrollListener mDetectListener;

    public RecyclerViewItemDetector(RecyclerView recyclerView, int topOffset, int bottomOffset) {
        super(recyclerView, topOffset, bottomOffset);
        mDetectListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                detectItem(recyclerView);
            }
        };
    }

    @Override
    public void start() {
        super.start();
        if (mDetectListener != null) {
            final RecyclerView view = mTargetView;
            view.removeOnScrollListener(mDetectListener);
            view.addOnScrollListener(mDetectListener);
        }
    }

    @Override
    public void stop() {
        final RecyclerView view = mTargetView;
        view.removeOnScrollListener(mDetectListener);
        super.stop();
    }

    @Override
    protected int getChildCount(RecyclerView recyclerView) {
        return recyclerView.getChildCount();
    }

    @Override
    protected int indexOfChild(RecyclerView recyclerView, View view) {
        return recyclerView.indexOfChild(view);
    }

    @Override
    protected View getChildAt(RecyclerView recyclerView, int index) {
        return getLayoutManager(recyclerView).getChildAt(index);
    }

    @Override
    protected int getFirstVisibleIndex(RecyclerView recyclerView) {
        return getLayoutManager(recyclerView).findFirstVisibleItemPosition();
    }

    private LinearLayoutManager getLayoutManager(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (!(manager instanceof LinearLayoutManager)) {
            throw new UnsupportedOperationException("Support LinearLayoutManager only, but current is "
                    + (manager != null ? manager.getClass().getSimpleName() : null));
        }

        return (LinearLayoutManager) manager;
    }

}
