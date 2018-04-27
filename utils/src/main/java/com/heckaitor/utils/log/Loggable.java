package com.heckaitor.utils.log;

interface Loggable {
    void log(int priority, Object caller, Throwable throwable, Object... messages);
}
