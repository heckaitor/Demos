package com.heckaitor.autoplay;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import static com.heckaitor.autoplay.DetectableItemHelper.activateItem;
import static com.heckaitor.autoplay.DetectableItemHelper.deactivateItem;
import static com.heckaitor.autoplay.DetectableItemHelper.isItemActive;

/**
 * 互斥Item检测，同一时间只能有一个被激活，如视频、gif、story等
 * Created by kaige1 on 14/12/2017.
 */
public abstract class ExclusiveItemDetector<TargetView extends ViewGroup> extends AbsViewItemDetector<TargetView> {

    public ExclusiveItemDetector(TargetView targetView, int topOffset, int bottomOffset) {
        super(targetView, topOffset, bottomOffset);
    }

    @Override
    @CallSuper
    public void stop() {
        super.stop();
        // stop之前如果有active的item，反激活
        final TargetView view = mTargetView;
        final DetectableItem item = findActiveItem(view);
        if (item != null) {
            deactivateItem(item);
        }
    }

    /**
     * 按照以下规则查找并激活对应的Item
     * <ul>
     *    <li>向上滑动
     *       <ol>
     *           <li>查找当前处于活跃状态的Item</li>
     *           <li>如果没有，从上到下检查第一个可被激活的Item</li>
     *           <li>如果有，检查活跃Item是否满足反激活的条件，满足，反激活该Item，
     *           并从该Item开始，自上向下开始检查第一个可被激活的Item</li>
     *       </ol>
     *    </li>
     *    <li>向下滑动
     *       <ol>
     *           <li>查找当前处于激活状态的Item</li>
     *           <li>自上而下查找一个满足条件的Item，如果这个Item就是当前活跃的，保持活跃状态，否则</li>
     *           <li>如果找到了新的Item，反激活当前活跃的Item，并激活这个</li>
     *           <li>如果没有找到，检查当前活跃的Item是否满足反激活条件，满足则反激活该Item</li>
     *       </ol>
     *    </li>
     * </ul>
     *
     * <p>如果有特殊规则，可重写该方法</p>
     *
     * @param viewGroup
     */
    @Override
    public void detectItem(@NonNull TargetView viewGroup) {
        if (!isEnable()
                || getChildCount(viewGroup) == 0) {
            return;
        }

        //VLogger.i(this, "detectItem", commonToString(viewGroup), "childCount = " + getChildCount(viewGroup));
        final int direction = detectDirection(viewGroup);
        //VLogger.d(this, "direction", direction2String(direction));
        switch (direction) {
            case UP: {
                final DetectableItem activeItem = findActiveItem(viewGroup);
                //VLogger.d(this, "findActiveItem", commonToString(activeItem));
                if (activeItem != null) {
                    if (checkDeactivateIfNecessary(activeItem)) {
                        deactivateItem(activeItem);
                        DetectableItem item = findItemUpToDown(viewGroup, activeItem);
                        activateItem(item);
                    }
                } else {
                    DetectableItem item = findItemUpToDown(viewGroup, null);
                    activateItem(item);
                }
                break;
            }
            case DOWN: {
                final DetectableItem activeItem = findActiveItem(viewGroup);
                //VLogger.d(this, "findActiveItem", commonToString(activeItem));

                final DetectableItem item = findItemUpToDown(viewGroup, null);
                if (item != null) {
                    if (item != activeItem) {
                        deactivateItem(activeItem);
                        activateItem(item);
                    }
                } else {
                    if (checkDeactivateIfNecessary(activeItem)) {
                        deactivateItem(activeItem);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * 当前正在活跃状态的Item
     * @param viewGroup
     * @return
     */
    private DetectableItem findActiveItem(TargetView viewGroup) {
        final int count = getChildCount(viewGroup);
        for (int i = 0; i < count; i++) {
            final DetectableItem item = findItemAtIndex(viewGroup, i);
            if (isItemActive(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 查找ViewGroup第index位置的ItemView，并且该Item支持探测
     * @param viewGroup
     * @param index
     * @return
     */
    private DetectableItem findItemAtIndex(TargetView viewGroup, int index) {
        final View itemView = getChildAt(viewGroup, index);
        if (itemView instanceof DetectableItem) {
            return (DetectableItem) itemView;
        }
        return null;
    }

    /**
     * 从活跃Item的下一个开始，自上而下查找一个满足条件的Item；
     * 如果没有活跃Item，则从第一个开始
     * @param viewGroup
     * @param activeItem
     * @return
     */
    private DetectableItem findItemUpToDown(TargetView viewGroup, DetectableItem activeItem) {
        // 1.计算当前活跃Item的View index
        int index;
        if (activeItem instanceof View) {
            index = indexOfChild(viewGroup, (View) activeItem);
            if (index < 0) {
                index = -1;
            }
        } else {
            index = -1; // 当前没有活跃的Item
        }

        // 2. 从index的下一个开始，自上到下查找
        index++;
        //VLogger.v(this, "findNext", "start from index = " + index);

        final int count = getChildCount(viewGroup);
        for (int i = index; i < count; i++) {
            // 3. 寻找特定的Item
            final DetectableItem item = findItemAtIndex(viewGroup, i);
            if (item != null) {
                // 4. 检查Item满足激活的条件
                if (checkActivateIfNecessary(item)) {
                    return item;
                }
            }
        }
        return null;
    }
}
