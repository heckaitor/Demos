package com.heckaitor.demo.utils.log;

interface Loggable {
    void log(int priority, Object tag, Throwable throwable, String... messages);
}
