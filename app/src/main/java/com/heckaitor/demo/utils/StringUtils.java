package com.heckaitor.demo.utils;

/**
 * Created by kaige1 on 21/07/2017.
 */
public class StringUtils {
    
    public static String commonToString(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.getClass().getSimpleName() + "@" + Integer.toHexString(o.hashCode());
        }
    }
}
