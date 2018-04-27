package com.heckaitor.utils;

/**
 * Created by kaige1 on 21/07/2017.
 */
public class StringUtils {

    /**
     * 获取一个对象在普通意义下的toString：类名@hashcode
     * @param o
     * @return
     */
    public static String commonToString(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.getClass().getSimpleName() + "@" + Integer.toHexString(o.hashCode());
        }
    }
}
