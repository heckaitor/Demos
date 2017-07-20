package com.heckaitor.demo.utils.log;

import android.content.Context;

public class LogNodeFactory {
    
    public enum LoggerType {
        TERMINAL,
        WINDOW
    }
    
   public static final LogNode create(Context context, LoggerType type) {
       switch (type) {
           case TERMINAL: return new TerminalLog();
           case WINDOW:   return WindowLog.create(context);
           default: throw new UnsupportedOperationException("Logger " + type + " is NOT supported!");
       }
   }
}
