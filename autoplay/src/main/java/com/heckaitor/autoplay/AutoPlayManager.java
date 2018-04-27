package com.heckaitor.autoplay;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.ViewGroup;

/**
 * 自动播放，典型用法：
 * AutoPlayManager.bind(ListView).topOffset(TitleBarHeight).bottomOffset(ListView.height - TabBar.Height).detect()
 *
 * Created by kaige1 on 16/08/2017.
 */
public class AutoPlayManager {

    /**
     * 初始化自动播放检测机制，实际是添加一个空的Fragment
     * @param context
     * @return
     */
    public static AutoPlaybackFragment init(Context context) {
        AutoPlaybackFragment fragment = find(context);
        if (fragment == null) {
            final Activity activity = reviseActivity(context);
            if (activity != null) {
                fragment = AutoPlaybackFragment.create();
                try {
                    FragmentManager manager = activity.getFragmentManager();
                    manager.beginTransaction().add(fragment, AutoPlaybackFragment.TAG).commit();
                    manager.executePendingTransactions();
                } catch (Exception e) {
                    // WBANDROIDCRASH-2600
                    // 在Activity.onCreate中执行FragmentTransaction.commit也能出现illegalStateException？？
                }
            }
        }
        return fragment;
    }

    /**
     * 在一个ViewGroup上绑定自动检测机制
     * @param viewGroup
     * @return
     */
    public static Builder bind(ViewGroup viewGroup) {
        return new Builder(viewGroup);
    }

    /**
     * 手动检测，并激活item
     * @param targetView
     */
    public static void activate(final ViewGroup targetView) {
        // 手动检测主要用于多选项卡页面（ViewPager + Fragment），如feed，cardlist，profile等，
        // 每个Fragment中都有一个ListView绑定自动检测机制，
        // 在切换不同的选项卡时，需要手动激活对应页面的自动检测。
        // 如果Activity中只有一个流，无须手动，自动播放机制就可以解决绝大部分问题
        if (targetView != null) {
            // delay的原因：
            // 在使用ViewPager + Fragment的页面中，手动点击tab切换ViewPager，
            // 无论在Fragment.userVisibleHint、Fragment.onResume、ViewPager.onPageSelected、ViewTreeObserver.GlobalLayout、ViewTreeObserver.onFocusChanged等等回调中，
            // 通过View.getLocationInWindow、View.getGlobalVisibleRect等方法，计算出来的View的可见区域均为错误的结果，导致自动检测失败。
            // 因此延迟处理，延迟时间为经验值，不能保证延迟后计算的结果仍然正确。。
            // 另外，滑动ViewPager进行切换并不会导致计算错误，所以延迟会牺牲这部分交互
            targetView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (targetView == null) {
                        return;
                    }

                    final Context context = targetView.getContext();
                    if (context == null) {
                        return;
                    }

                    // 全屏模式下，应当屏蔽自动播放
                    final Activity activity = reviseActivity(context);
                    final AutoPlaybackFragment instance = find(context);
                    if (instance != null) {
                        //VLogger.d(activity, instance + ".activate: " + targetView);
                        instance.activate(targetView);
                    }
                }
            }, 300);
        }
    }

    private static AutoPlaybackFragment find(Context context) {
        final Activity activity = reviseActivity(context);
        if (activity != null) {
            FragmentManager manager = activity.getFragmentManager();
            final AutoPlaybackFragment fragment = (AutoPlaybackFragment) manager.findFragmentByTag(AutoPlaybackFragment.TAG);
            return fragment;
        }
        return null;
    }

    /**
     * 解析需要添加Fragment的Activity，兼容首页ActivityGroup
     * @param context
     * @return
     */
    private static Activity reviseActivity(Context context) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("auto play should run in Activity");
        }

        final Activity activity = (Activity) context;
        final Activity parentActivity = activity.getParent();
        return parentActivity != null ? parentActivity : activity;
    }

    public static class Builder {
        private ViewGroup targetView;
        private int topOffset = 0;
        private int bottomOffset = 0;
        //... 可根据需要扩展left、right，支持横向自动播放的检测

        /*default*/Builder(ViewGroup targetView) {
            this.targetView = targetView;
        }

        /**
         * 默认的激活、反激活是计算每个ItemView的可见区域占比。但可以设置top偏移量，计算可见性时会去掉这部分高度。
         * 比如：视频流使用透明标题栏，虽然ItemView在标题栏下面时仍然是可见的，但计算时需要去掉这部分高度；
         * 或者profile，cardlist等，页面头部有特殊布局，计算时需要去掉遮挡的这部分高度
         * @param offset
         * @return
         */
        public Builder topOffset(int offset) {
            this.topOffset = offset;
            return this;
        }

        /**
         * 同{@link #topOffset(int)}，注意该值为TargetView的高度减去底部被遮挡的高度，如tab条：ListView.height - TabBar.height
         * @param offset
         * @return
         */
        public Builder bottomOffset(int offset) {
            this.bottomOffset = offset;
            return this;
        }

        /**
         * 检测对应的Item
         */
        public void detect() {
            if (targetView == null) {
                return;
            }

            final AutoPlaybackFragment fragment = find(targetView.getContext());
            if (fragment == null) {
                return;
            }

            fragment.bind(targetView, topOffset, bottomOffset);
        }
    }

}
