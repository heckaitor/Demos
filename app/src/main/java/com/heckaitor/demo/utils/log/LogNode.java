package com.heckaitor.demo.utils.log;

import android.text.TextUtils;

import com.heckaitor.demo.utils.log.LogNodeFactory.LoggerType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

public abstract class LogNode implements Loggable {
    
    private LogNode mNext;
    
    @Override
    public void log(int priority, Object tag, Throwable throwable, String... messages) {
        final String TAG = generateTag(tag);
        StringBuilder builder = new StringBuilder(getStackTraceString(throwable));
        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                final String message = messages[i];
                if (!TextUtils.isEmpty(message)) {
                    builder.append(message);
                }
                
                if (i < messages.length - 1) {
                    builder.append(" -> ");
                }
            }
        }
                
        final String content = builder.toString();
        print(priority, TAG, content);
        
        if (mNext != null) {
            mNext.print(priority, TAG, content);
        }
    }
    
    public abstract LoggerType getType();
    
    public abstract void print(int priority, String tag, String message);
    
    public void setNext(LogNode next) {
        this.mNext = next;
    }
    
    public LogNode getNext() {
        return this.mNext;
    }
    
    public abstract void destroySelf();
    
    private String generateTag(Object tag) {
        final String tagString = tag != null ? tag.getClass().getSimpleName() : "";
        return tagString + "(" + attachThreadId() + ")";
    }
    
    private String attachThreadId() {
        return String.valueOf(Thread.currentThread().getId());
    }
    
    private String getStackTraceString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
    
        Throwable t = throwable;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
    
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
    
}
