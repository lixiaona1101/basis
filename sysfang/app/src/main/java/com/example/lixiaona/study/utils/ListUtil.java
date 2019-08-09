package com.example.lixiaona.study.utils;

import java.util.List;

/**
 * 作    用:  集合有关
 * 注意事项:
 */

public class ListUtil {

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static int getSize(List list) {
        if (list == null)
            return 0;
        return list.size();
    }

    public static <T> int getSize(T[] objs) {
        if (objs == null)
            return 0;
        return objs.length;
    }

    public static int getSize(int[] is) {
        if (is == null)
            return 0;
        return is.length;
    }
}
