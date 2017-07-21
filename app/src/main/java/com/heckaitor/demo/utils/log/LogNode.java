package com.heckaitor.demo.utils.log;

import com.heckaitor.demo.utils.log.LogNodeFactory.LoggerType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

public abstract class LogNode implements Loggable {
    
    private LogNode mNext;
    
    @Override
    public void log(int priority, Object caller, Throwable throwable, Object... messages) {
        final String TAG = generateTag(caller);
        StringBuilder builder = new StringBuilder(getStackTraceString(throwable));
        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                final Object message = messages[i];
                builder.append(message);
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
    
    /**
     * callerClassName(threadId)
     * @param caller
     * @return
     */
    private String generateTag(Object caller) {
        final String tagString = caller != null ? caller.getClass().getSimpleName() : "null";
        return tagString + "(" + Thread.currentThread().getId() + ")";
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
