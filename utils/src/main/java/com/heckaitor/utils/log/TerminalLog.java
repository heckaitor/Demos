package com.heckaitor.utils.log;

import android.util.Log;

public class TerminalLog extends LogNode {
    
    @Override
    public LogNodeFactory.LoggerType getType() {
        return LogNodeFactory.LoggerType.TERMINAL;
    }
    
    @Override
    public void print(int priority, String tag, String message) {
        switch (priority) {
            case Logger.VERBOSE: Log.v(tag, message); break;
            case Logger.DEBUG: Log.d(tag, message); break;
            case Logger.INFO: Log.i(tag, message); break;
            case Logger.WARN: Log.w(tag, message); break;
            case Logger.ERROR: Log.e(tag, message); break;
            default: break;
        }
    }
    
    @Override
    public void destroySelf() { }
}
