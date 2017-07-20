package com.heckaitor.demo.utils.log;

import android.content.Context;

import com.heckaitor.demo.utils.log.LogNodeFactory.LoggerType;

public class Logger {
    
    private static LogNode mNode = new TerminalLog();
    
    public static final int VERBOSE = 2;
    public static final int DEBUG   = 3;
    public static final int INFO    = 4;
    public static final int WARN    = 5;
    public static final int ERROR   = 6;
    
    public static void v(Object tag, String... messages) {
        print(VERBOSE, tag, null, messages);
    }
    
    public static void d(Object tag, String... messages) {
        print(DEBUG, tag, null, messages);
    }
    
    public static void i(Object tag, String... messages) {
        print(INFO, tag, null, messages);
    }
    
    public static void w(Object tag, String... messages) {
        print(WARN, tag, null, messages);
    }
    
    public static void e(Object tag, Throwable throwable, String... messages) {
        print(ERROR, tag, throwable, messages);
    }
    
    private static void print(int priority, Object tag, Throwable throwable, String... messages) {
        if (mNode != null) {
            mNode.log(priority, tag, throwable, messages);
        }
    }
    
    public static final void addLogNodeIfNonExist(Context context, LoggerType type) {
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
    
    public static final void removeLogNodeIfExist(LoggerType type) {
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
