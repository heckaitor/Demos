package com.heckaitor.autoplay;

/**
 * 适用于{@link IViewItemDetector}的Item。
 * 一般情况下，{@link DetectableItem}通过计算View的可见性即可实现Item的激活、反激活，
 * 但是，如果Item有自身特殊的策略，可同时实现此接口，定制激活、反激活的规则。如九宫格的GIF顺序 + 循环播放
 * Created by kaige1 on 05/01/2018.
 */
public interface DetectRules {

    /**
     * 检测当前的Item是否满足激活的条件
     * @return
     */
    boolean checkActivateIfNecessary();

    /**
     * 检测当前的Item是否满足反激活的条件
     * @return
     */
    boolean checkDeactivateIfNecessary();
}
