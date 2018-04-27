package com.heckaitor.autoplay;

import android.view.View;

import com.heckaitor.utils.log.Logger;

import static com.heckaitor.utils.StringUtils.commonToString;

/**
 * {@link DetectableItem}的工具
 * Created by kaige1 on 22/01/2018.
 */
public class DetectableItemHelper {

    private static final String TAG = DetectableItemHelper.class.getSimpleName();

    /**
     * 检查Item是否处于被激活的状态
     * @param item
     * @return
     */
    /*package*/ static boolean isItemActive(DetectableItem item) {
        final View view = item != null ? item.getDetectedView() : null;
        if (view != null) {
            final Object state = view.getTag(R.id.detectable_item_active_state);
            return state != null && (Boolean) state;
        }
        return false;
    }

    /**
     * 对目标Item的View标记一个特殊的tag，表示激活状态
     * @param item
     * @param state
     */
    /*package*/ static void markItemActiveState(DetectableItem item, boolean state) {
        final View view = item.getDetectedView();
        if (view != null) {
            view.setTag(R.id.detectable_item_active_state, state);
        }
    }

    /**
     * 激活Item
     * @param item
     */
    public static void activateItem(DetectableItem item) {
        if (item != null && !isItemActive(item)) {
            Logger.i(TAG, "activateItem", commonToString(item));
            markItemActiveState(item, true);
            item.activate();
        }
    }

    /**
     * 反激活Item
     * @param item
     */
    public static void deactivateItem(DetectableItem item) {
        if (item != null && isItemActive(item)) {
            Logger.w(TAG, "deactivateItem", commonToString(item));
            markItemActiveState(item, false);
            item.deactivate();
        }
    }
}
