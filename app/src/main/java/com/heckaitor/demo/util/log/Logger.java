package com.heckaitor.demo.util.log;

import android.content.Context;

public class Logger {
    
    private static LogNode mNode = new TerminalLog();
    
    public static final int VERBOSE = 2;
    public static final int DEBUG   = 3;
    public static final int INFO    = 4;
    public static final int WARN    = 5;
    public static final int ERROR   = 6;
    
    public static void v(Object caller, Object... messages) {
        print(VERBOSE, caller, null, messages);
    }
    
    public static void d(Object caller, Object... messages) {
        print(DEBUG, caller, null, messages);
    }
    
    public static void i(Object caller, Object... messages) {
        print(INFO, caller, null, messages);
    }
    
    public static void w(Object caller, Object... messages) {
        print(WARN, caller, null, messages);
    }
    
    public static void e(Object caller, Throwable throwable, Object... messages) {
        print(ERROR, caller, throwable, messages);
    }
    
    private static void print(int priority, Object caller, Throwable throwable, Object... messages) {
        if (mNode != null) {
            mNode.log(priority, caller, throwable, messages);
        }
    }
    
    public static final void addLogNodeIfNonExist(Context context, LogNodeFactory.LoggerType type) {
        if (mNode == null) {
            mNode = LogNodeFactory.create(context, type);
            return;
        }
    
        LogNode p = mNode;
        LogNode last = mNode;
        while (p != null) {
            if (type == p.getType()) {
                return; // exist
            }
        
            last = p;
            p = p.getNext();
        }
    
        LogNode node = LogNodeFactory.create(context, type);
        last.setNext(node);
    }
    
    public static final void removeLogNodeIfExist(LogNodeFactory.LoggerType type) {
        LogNode p = mNode, prev = null;
        while (p != null) {
            if (type == p.getType()) {
                break;
            }
        
            if (p.getNext() == null) {
                return;
            }
        
            prev = p;
            p = p.getNext();
        }
    
        if (p != null) {
            // exist, remove it
            if (prev == null) {
                mNode = p.getNext();
            } else {
                prev.setNext(p.getNext());
            }
            p.destroySelf();
        }
    }
    
}
