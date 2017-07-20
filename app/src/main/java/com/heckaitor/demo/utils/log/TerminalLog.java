package com.heckaitor.demo.utils.log;

import android.util.Log;

import static com.heckaitor.demo.utils.log.Logger.DEBUG;
import static com.heckaitor.demo.utils.log.Logger.ERROR;
import static com.heckaitor.demo.utils.log.Logger.INFO;
import static com.heckaitor.demo.utils.log.Logger.VERBOSE;
import static com.heckaitor.demo.utils.log.Logger.WARN;

public class TerminalLog extends LogNode {
    
    @Override
    public LogNodeFactory.LoggerType getType() {
        return LogNodeFactory.LoggerType.TERMINAL;
    }
    
    @Override
    public void print(int priority, String tag, String message) {
        switch (priority) {
            case VERBOSE: Log.v(tag, message); break;
            case DEBUG: Log.d(tag, message); break;
            case INFO: Log.i(tag, message); break;
            case WARN: Log.w(tag, message); break;
            case ERROR: Log.e(tag, message); break;
            default: break;
        }
    }
    
    @Override
    public void destroySelf() { }
}
