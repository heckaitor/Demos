package com.heckaitor.autoplay;

import android.view.View;

/**
 * 适用于{@link IViewItemDetector}的Item，
 * 通过计算{@link #getDetectedView()}的可见性，调用Item的激活或反激活方法
 * Created by kaige1 on 21/07/2017.
 */
public interface DetectableItem {

    /**
     * @return 用于计算的View
     */
    View getDetectedView();
    
    /**
     * 激活
     */
    void activate();
    
    /**
     * 反激活
     */
    void deactivate();

}
