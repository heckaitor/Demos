package com.heckaitor.demo;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.heckaitor.demo.util.log.LogNodeFactory;
import com.heckaitor.demo.util.log.Logger;
import com.squareup.leakcanary.LeakCanary;

import static com.heckaitor.demo.SettingsActivity.TERMINAL_LOG_ENABLE;
import static com.heckaitor.demo.SettingsActivity.WINDOW_LOG_ENABLE;

public class DemoApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        
        registerActivityLifecycleCallbacks(mInnerActivityLifecycleCallback);
        initLogger();
    }
    
    private void initLogger() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean(TERMINAL_LOG_ENABLE, false)) {
            Logger.addLogNodeIfNonExist(this, LogNodeFactory.LoggerType.TERMINAL);
        } else {
            Logger.removeLogNodeIfExist(LogNodeFactory.LoggerType.TERMINAL);
        }
    
        if (pref.getBoolean(WINDOW_LOG_ENABLE, false)) {
            Logger.addLogNodeIfNonExist(this, LogNodeFactory.LoggerType.WINDOW);
        } else {
            Logger.removeLogNodeIfExist(LogNodeFactory.LoggerType.WINDOW);
        }
    }
    
    private ActivityLifecycleCallbacks mInnerActivityLifecycleCallback = new ActivityLifecycleCallbacks() {
        
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onCreate");
        }
    
        @Override
        public void onActivityStarted(Activity activity) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onStart");
        }
    
        @Override
        public void onActivityResumed(Activity activity) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onResume");
        }
    
        @Override
        public void onActivityPaused(Activity activity) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onPause");
        }
    
        @Override
        public void onActivityStopped(Activity activity) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onStop");
        }
    
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onSaveInstanceState");
        }
    
        @Override
        public void onActivityDestroyed(Activity activity) {
            Logger.i(getApplicationContext(), activity.getClass().getSimpleName(), "onDestroy");
        }
    };
}
