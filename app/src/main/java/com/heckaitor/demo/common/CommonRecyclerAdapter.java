package com.heckaitor.demo.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用{@link android.support.v7.widget.RecyclerView.Adapter}，支持
 * <ul>
 *     <li>empty View：{@link #setEmptyView(View)}</li>
 *     <li>header：{@link #addHeaderView(View)}</li>
 *     <li>footer：{@link #addFooterView(View)}</li>
 * </ul>
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter {

    private static final int HEADER_VIEW_TYPE_INDEX = 100;
    private static final int FOOTER_VIEW_TYPE_INDEX = 200;
    private static final int EMPTY_VIEW = 300;

    private SparseArray<View> mHeaderViews = new SparseArray<>(2);
    private SparseArray<View> mFooterViews = new SparseArray<>(2);
    private View mEmptyView;

    private RecyclerView.Adapter mInnerAdapter;

    public CommonRecyclerAdapter(@NonNull RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            return CommonViewHolder.create(mEmptyView);
        }

        View view = mHeaderViews.get(viewType);
        if (view != null) {
            return CommonViewHolder.create(view);
        }

        view = mFooterViews.get(viewType);
        if (view != null) {
            return CommonViewHolder.create(view);
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isHeaderPosition(position)
                || isEmptyPosition(position)
                || isFooterPosition(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(viewHolder, position - getHeaderCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }

        if (isEmptyPosition(position)) {
            return EMPTY_VIEW;
        }

        if (isFooterPosition(position)) {
            return mFooterViews.keyAt(position - getHeaderCount()
                    - (shouldShowEmpty() ? 1 : getRealItemCount()));
        }
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        int count = getHeaderCount() + getFooterCount();
        if (shouldShowEmpty()) {
            count++;//empty view
        } else {
            count += getRealItemCount();
        }
        return count;
    }

    /**
     * @return header数量
     */
    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    /**
     * @return footer数量
     */
    public int getFooterCount() {
        return mFooterViews.size();
    }

    /**
     * @return 去掉header和footer后，真实的item数量
     */
    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    private boolean isEmptyPosition(int position) {
        return shouldShowEmpty() && position == getHeaderCount();
    }

    private boolean shouldShowEmpty() {
        return getRealItemCount() == 0 && mEmptyView != null;
    }

    private boolean isFooterPosition(int position) {
        int itemCount = getRealItemCount();
        if (itemCount == 0 && mEmptyView != null) {
            itemCount = 1;
        }
        return position >= getHeaderCount() + itemCount;
    }

    /**
     * 设置没有数据时的空view
     * @param view
     */
    public void setEmptyView(View view) {
        mEmptyView = view;
    }

    public void addHeaderView(View view) {
        if (view != null) {
            mHeaderViews.put(HEADER_VIEW_TYPE_INDEX + mHeaderViews.size(), view);
        }
    }

    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index >= 0) {
            mHeaderViews.remove(index);
        }
    }

    public void addFooterView(View view) {
        if (view != null) {
            mFooterViews.put(FOOTER_VIEW_TYPE_INDEX + mFooterViews.size(), view);
        }
    }

    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index >= 0) {
            mFooterViews.remove(index);
        }
    }

    private static class CommonViewHolder extends RecyclerView.ViewHolder {

        public static CommonViewHolder create(View view) {
            return new CommonViewHolder(view);
        }

        private CommonViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);

        // header, footer适配GridLayoutManager
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            final GridLayoutManager.SpanSizeLookup oldLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    final int viewType = getItemViewType(position);
                    if (mHeaderViews.indexOfKey(viewType) >= 0) {
                        return gridManager.getSpanCount();
                    } else if (mFooterViews.indexOfKey(viewType) >= 0) {
                        return gridManager.getSpanCount();
                    } else {
                        return oldLookup.getSpanSize(position);
                    }
                }
            });
            gridManager.setSpanCount(gridManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mInnerAdapter.onViewAttachedToWindow(holder);

        // header, footer适配StaggeredGridLayoutManager
        final int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }
}
