package com.heckaitor.demo;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.heckaitor.demo.util.log.LogNodeFactory;
import com.heckaitor.demo.util.log.Logger;

public class SettingsActivity extends AppCompatActivity {
    
    public static final String WINDOW_LOG_ENABLE    = "window_log_enable";
    public static final String TERMINAL_LOG_ENABLE = "terminal_log_enable";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
    
    public static class SettingsFragment extends PreferenceFragment {
    
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preference);
        }
    
        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            Logger.i(this, "onPreferenceTreeClick", preference.getKey(), preference.getClass().getSimpleName());
            switch (preference.getKey()) {
                case TERMINAL_LOG_ENABLE: {
                    SwitchPreference switchPreference = (SwitchPreference) preference;
                    checkLogger(LogNodeFactory.LoggerType.TERMINAL, switchPreference.isChecked());
                    break;
                }
                case WINDOW_LOG_ENABLE: {
                    SwitchPreference switchPreference = (SwitchPreference) preference;
                    checkLogger(LogNodeFactory.LoggerType.WINDOW, switchPreference.isChecked());
                    break;
                }
                default: break;
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        
        private void checkLogger(LogNodeFactory.LoggerType type, boolean enable) {
            if (enable) {
                Logger.addLogNodeIfNonExist(getActivity(), type);
            } else {
                Logger.removeLogNodeIfExist(type);
            }
        }
    
    }
    
}
