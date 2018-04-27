package com.heckaitor.autoplay;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * 在一个{@link ViewGroup}中，按照一定的规则进行探测，自动激活Item
 *
 * Created by kaige1 on 27/07/2017.
 */
public interface IViewItemDetector<TargetView extends ViewGroup> {
    
    /**
     * 计算所有的item，并按照规则进行Item的反激活和激活
     * @param viewGroup
     */
    void detectItem(@NonNull TargetView viewGroup);

    /**
     * 开始探测，并激活Item
     */
    void start();

    /**
     * 停止探测，并反激活处于活跃状态的Item
     */
    void stop();

    /**
     * 暂停探测：
     * 不同于{@link #stop()}，暂停后只是不再进行计算，并不改变当前活跃的item，
     * 直到{@link #resume()}开始重新探测计算
     */
    void pause();

    /**
     * 重新开始探测，并激活符合规则的Item
     */
    void resume();
}
