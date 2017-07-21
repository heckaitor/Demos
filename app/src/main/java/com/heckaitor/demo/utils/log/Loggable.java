package com.heckaitor.demo.utils.log;

interface Loggable {
    void log(int priority, Object caller, Throwable throwable, Object... messages);
}
