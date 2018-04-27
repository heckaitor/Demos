package com.heckaitor.autoplay;

/**
 * 与{@link DetectRules}一样，自定义检测时激活、反激活的规则。
 * 另外，{@link IViewItemDetector}如果设置了offset，检测时需要计算此部分，
 * offset的说明详见{@link AutoPlayManager.Builder#topOffset(int)}、{@link AutoPlayManager.Builder#bottomOffset(int)}
 *
 * Created by kaige1 on 05/01/2018.
 */
public interface DetectRules2 {

    /**
     * 检测当前的Item是否满足激活的条件
     * @param topOffset
     * @param bottomOffset
     * @return
     */
    boolean checkActivateIfNecessary(int topOffset, int bottomOffset);

    /**
     * 检测当前的Item是否满足反激活的条件
     * @param topOffset
     * @param bottomOffset
     * @return
     */
    boolean checkDeactivateIfNecessary(int topOffset, int bottomOffset);
}