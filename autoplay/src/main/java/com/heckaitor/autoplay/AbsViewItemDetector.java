package com.heckaitor.autoplay;

import android.view.View;
import android.view.ViewGroup;

import static com.heckaitor.autoplay.ViewVisibilityHelper.calculateVisiblePercents;
import static com.heckaitor.autoplay.ViewVisibilityHelper.calculateVisiblePercentsWithTopOffset;

/**
 * 在一个ViewGroup中按照一定的策略查找特定的ItemView，并对响应的Item进行激活和反激活，
 * 用于视频、360图片等在滑动过程中的自动播放
 *
 * <p>这只是一个基础的实现，与具体的ViewGroup和业务（Item类型）无关，因此可以方便的集成到相似的场景中。</p>
 *
 * Created by kaige1 on 14/12/2017.
 */
public abstract class AbsViewItemDetector<TargetView extends ViewGroup> implements IViewItemDetector<TargetView> {

    protected static final int UP   = 1;
    protected static final int DOWN = 2;

    private int mFirstItemIndex; // item的在ViewGroup数据列表中的index
    private int mFirstItemTop;

    protected TargetView mTargetView;

    /**
     * 默认的激活、反激活是计算每个ItemView的可见区域占比。但可以设置top偏移量，计算可见性时会去掉这部分高度。
     * 比如：视频流使用透明标题栏，虽然ItemView在标题栏下面时仍然是可见的，但计算时需要去掉这部分高度；
     * 或者profile，cardlist等，页面头部有特殊布局，计算时需要去掉遮挡的这部分高度
     * */
    protected int mTopOffset;

    /**
     * 同{@link #mTopOffset}，
     * 注意该值为TargetView的高度减去底部被遮挡的高度，如tab条：ListView.height - TabBar.height
     * */
    protected int mBottomOffset;

    protected boolean mEnable = true;

    public AbsViewItemDetector(TargetView mTargetView, int topOffset, int bottomOffset) {
        this.mTargetView = mTargetView;
        this.mTopOffset = topOffset;
        this.mBottomOffset = bottomOffset;
    }

    @Override
    public void start() {
        //VLogger.i(this, "start");
        mEnable = true;
        detectItem(mTargetView);
    }

    @Override
    public void pause() {
        //VLogger.i(this, "pause");
        mEnable = false;
    }

    @Override
    public void resume() {
        //VLogger.i(this, "resume");
        mEnable = true;
    }

    @Override
    public void stop() {
        //VLogger.w(this, "stop");
        mEnable = false;
    }

    public boolean isEnable() {
        return mEnable;
    }

    /**
     * Item是否要反激活
     * @param item
     * @return
     */
    protected boolean checkDeactivateIfNecessary(DetectableItem item) {
        if (item == null) {
            return true; //throw NPE!
        }

        final View itemView = item.getDetectedView();
        if (itemView == null) {
            return true;
        }

        // 若item定制了激活的策略，使用其规则
        if (itemView instanceof DetectRules) {
            return ((DetectRules) itemView).checkDeactivateIfNecessary();
        }

        if (itemView instanceof DetectRules2) {
            return ((DetectRules2) itemView).checkDeactivateIfNecessary(mTopOffset, mBottomOffset);
        }

        // 默认规则：计算View的可见区域占比
        if (mTopOffset > 0) {
            return calculateVisiblePercentsWithTopOffset(itemView, mTopOffset) < 0.5f;
        } else {
            return calculateVisiblePercents(itemView) < 0.5f;
        }
    }

    /**
     * Item是否需要激活
     * @param item
     * @return
     */
    protected boolean checkActivateIfNecessary(DetectableItem item) {
        if (item == null) {
            return false; //throw NPE!
        }

        final View itemView = item.getDetectedView();
        if (itemView == null) {
            return false;
        }

        // 若item定制了反激活的策略，使用其规则
        if (itemView instanceof DetectRules) {
            return ((DetectRules) itemView).checkActivateIfNecessary();
        }

        if (itemView instanceof DetectRules2) {
            return ((DetectRules2) itemView).checkActivateIfNecessary(mTopOffset, mBottomOffset);
        }

        // 默认规则：计算View的可见区域占比
        if (mTopOffset > 0) {
            return calculateVisiblePercentsWithTopOffset(itemView, mTopOffset) > 0.5f;
        } else {
            return calculateVisiblePercents(itemView) > 0.5f;
        }
    }

    /**
     * 检测当前滑动的方向
     * @param viewGroup
     * @return {@link #UP} 向上滚动；{@link #DOWN} 向下滚动
     */
    protected int detectDirection(TargetView viewGroup) {
        int direction = -1;
        if (getChildCount(viewGroup) > 0) {
            final int index = getFirstVisibleIndex(viewGroup);
            final View view = getChildAt(viewGroup, 0);
            final int top = view != null ? view.getTop() : 0;
            if (index == mFirstItemIndex) {
                if (top > mFirstItemTop) {
                    direction = DOWN;
                } else {
                    direction = UP;
                }
            } else {
                if (index > mFirstItemIndex) {
                    direction = UP;
                } else {
                    direction = DOWN;
                }
            }

            mFirstItemIndex = index;
            mFirstItemTop = top;
        }

        return direction;
    }

    protected String direction2String(int direction) {
        switch (direction) {
            case UP: return "↑";
            case DOWN: return "↓";
            default: return "unknown";
        }
    }

    protected abstract int getChildCount(TargetView viewGroup);

    protected abstract int indexOfChild(TargetView viewGroup, View view);

    protected abstract View getChildAt(TargetView viewGroup, int index);

    protected abstract int getFirstVisibleIndex(TargetView viewGroup);

}
