package com.heckaitor.autoplay;

import android.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 使用一个嵌入的Fragment管理检测的时机，控制自动播放
 * Created by kaige1 on 07/09/2017.
 */
public class AutoPlaybackFragment extends Fragment {

    public static final String TAG = "auto_playback";

    private SparseArray<IViewItemDetector> mDetectors = new SparseArray<>(1); //多标签页面可能会有多个ViewGroup检测

    static AutoPlaybackFragment create() {
        return new AutoPlaybackFragment();
    }

    void bind(ViewGroup targetViewGroup, int topOffset, int bottomOffset) {
        if (targetViewGroup == null) {
            return;
        }

        final int key = keyForView(targetViewGroup);
        IViewItemDetector detector = mDetectors.get(key);
        if (detector == null) {
            detector = DetectorAdapter.getDetector(targetViewGroup, topOffset, bottomOffset);
            mDetectors.put(key, detector);
            detector.start(); // 绑定后立即进行一次检测
        }
    }

    private int keyForView(View view) {
        return view != null ? view.hashCode() : 0;
    }

    /**
     * 手动检测，并激活item
     * @param targetView
     */
    void activate(ViewGroup targetView) {
        if (mDetectors == null || targetView == null) {
            return;
        }

        final int detectorKey = keyForView(targetView);
        for (int i = 0; i < mDetectors.size(); i++) {
            final int key = mDetectors.keyAt(i);
            final IViewItemDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                if (detectorKey == key) {
                    detector.start();
                } else {
                    detector.stop();
                }
            }
        }
    }

    /**
     * 手动检测，并激活item
     */
    void activateAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final IViewItemDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.start();
            }
        }
    }

    /**
     * 暂停检测
     *
     */
    void pauseAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final IViewItemDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.pause();
            }
        }
    }

    /**
     * 恢复检测
     */
    void resumeAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final IViewItemDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.resume();
            }
        }
    }

    /**
     * 手动检测活跃的item并反激活
     */
    void deactivateAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final IViewItemDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.stop();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        activateAll();
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        deactivateAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetectors.clear();
    }
}
