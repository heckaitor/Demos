package com.heckaitor.demo.util.log;

interface Loggable {
    void log(int priority, Object caller, Throwable throwable, Object... messages);
}
