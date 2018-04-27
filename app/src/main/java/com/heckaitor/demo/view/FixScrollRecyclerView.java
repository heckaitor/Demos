package com.heckaitor.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 在原生RecyclerView的基础上，改善了scrollToPosition的交互。
 * <ul>
 *     <li>scrollToPosition总能滑动到对应的位置，且定位到顶部</li>
 *     <li>可以通过scrollToPositionFromTop指定定位到顶部的距离</li>
 * </ul>
 * Created by kaige1 on 09/01/2018.
 */
public class FixScrollRecyclerView extends RecyclerView {

    private FixScrollListener mFixScrollListener = new FixScrollListener();

    public FixScrollRecyclerView(Context context) {
        this(context, null);
    }

    public FixScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addOnScrollListener(mFixScrollListener);
    }

    @Override
    public void scrollToPosition(int position) {
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            super.scrollToPosition(position);
            return;
        }
        scrollToPositionWithOffset(position, 0);
    }

    /**
     * 定位到position位置
     * @param position
     * @param offset 如方向VERTICAL，offset=10，表示定位到position且距离顶部的距离为10
     */
    public void scrollToPositionWithOffset(int position, int offset) {
        doScrollToPosition(position, offset, false);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            super.smoothScrollToPosition(position);
            return;
        }
        smoothScrollToPositionWithOffset(position, 0);
    }

    /**
     * 平顺滑动到position位置并定位
     * @param position
     * @param offset 如方向VERTICAL，offset=10，表示定位到position且距离顶部的距离为10
     */
    public void smoothScrollToPositionWithOffset(int position, int offset) {
        doScrollToPosition(position, offset, true);
    }

    private void doScrollToPosition(int position, int offset, boolean smooth) {
        final int itemCount = getAdapter() != null ? getAdapter().getItemCount() : 0;
        if (position < 0 || position > itemCount) {
            return; // throw IndexOutOfBoundException
        }

        // 仅支持LinearLayoutManager
        final LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        final int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        final int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

        if (position < firstVisiblePosition) {
            // 原生滚动即可满足定位
            if (offset != 0) {
                layoutManager.scrollToPositionWithOffset(position, offset);
            } else {
                if (smooth) {
                    super.smoothScrollToPosition(position);
                } else {
                    super.scrollToPosition(position);
                }
            }
        } else if (position < lastVisiblePosition) {
            // 屏幕内的Item，计算其位置并定位
            final View childView = getChildAt(position - firstVisiblePosition);
            if (childView != null) {
                if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    super.scrollBy(0, childView.getTop() - offset);
                } else {
                    super.scrollBy(childView.getLeft() - offset, 0);
                }
            }
        } else {
            // 最后一个可见Item之后，先滚动到对应的位置，再调整定位
            if (smooth) {
                super.smoothScrollToPosition(position);
            } else {
                super.scrollToPosition(position);
            }
            mFixScrollListener.mPosition = position;
            mFixScrollListener.mOffset = offset;
            mFixScrollListener.mSmooth = smooth;
        }
    }

    private class FixScrollListener extends OnScrollListener {

        private int mPosition = -1;
        private int mOffset = 0;
        private boolean mSmooth = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) { }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mPosition != -1) {
                if (!(getLayoutManager() instanceof LinearLayoutManager)) {
                    return;
                }

                // 仅支持LinearLayoutManager
                final LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                final int position = mPosition - layoutManager.findFirstVisibleItemPosition();
                if (position >= 0 && position < recyclerView.getChildCount()) {
                    final View childView = recyclerView.getChildAt(position);
                    if (childView != null) {
                        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                            if (mSmooth) {
                                recyclerView.smoothScrollBy(0, childView.getTop() - mOffset);
                            } else {
                                recyclerView.scrollBy(0, childView.getTop() - mOffset);
                            }
                        } else {
                            if (mSmooth) {
                                recyclerView.smoothScrollBy(childView.getLeft() - mOffset, 0);
                            } else {
                                recyclerView.scrollBy(childView.getLeft() - mOffset, 0);
                            }
                        }
                    }

                    mPosition = -1;
                    mOffset = 0;
                    mSmooth = false;
                }
            }
        }
    }

}
