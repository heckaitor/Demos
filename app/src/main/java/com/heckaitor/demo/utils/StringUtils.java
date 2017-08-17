package com.heckaitor.demo.utils;

import java.util.List;

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
    
    public static <T> String list2String(List<T> list) {
        if (list == null) {
            return "null";
        }
        
        StringBuilder builder = new StringBuilder("{");
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            T t = list.get(i);
            builder.append(t != null ? t.toString() : "null");
            if (i < size -1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }
    
    public static <T> String array2String(T... array) {
        if (array == null) {
            return "null";
        }
        
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            builder.append(t != null ? t.toString() : "null");
            
            if (i < array.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
