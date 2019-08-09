package com.example.lixiaona.study.utils;

import android.text.TextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合判断工具
 */
public class CollectionUtils {
    /**
     * 判断是否为空
     *
     * @return 空位true, 否则为false
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @return 空位true, 否则为false
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断是否包含字符串
     *
     * @return 包含返回true, 否则为false
     */
    public static boolean isContains(String[] arrs, String val) {
        if (arrs == null || TextUtils.isEmpty(val)) {
            return false;
        }
        for (String arrVal : arrs) {
            if (val.equals(arrVal)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContains(List<String> arrs, String val) {
        if (arrs == null || TextUtils.isEmpty(val)) {
            return false;
        }
        for (String arrVal : arrs) {
            if (val.equals(arrVal)) {
                return true;
            }
        }
        return false;
    }
}
