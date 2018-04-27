package com.heckaitor.autoplay;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 对不同的{@link View}进行适配，生成具体的探测器
 *
 * Created by kaige1 on 28/07/2017.
 */
public class DetectorAdapter {
    
    public static IViewItemDetector getDetector(@NonNull ViewGroup viewGroup, int topOffset, int bottomOffset) {
        if (viewGroup instanceof ListView) {
            return new ListViewItemDetector((ListView) viewGroup, topOffset, bottomOffset);
        }

        if (viewGroup instanceof RecyclerView) {
            return new RecyclerViewItemDetector(((RecyclerView) viewGroup), topOffset, bottomOffset);
        }
        
        return null;
    }
}
