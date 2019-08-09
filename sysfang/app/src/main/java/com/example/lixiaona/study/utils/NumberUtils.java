package com.example.lixiaona.study.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 数字工具类
 */

public class NumberUtils {
    /**
     * 获取随机int数字
     */
    public static int getRandomInteger(int from, int to) {
        Random random = new Random();
        return random.nextInt(to) % (to - from + 1) + from;
    }

    /**
     * 获取随机double数字
     */
    public static double getRandomDouble(double from, double to) {
        Random random = new Random();
        return random.nextDouble() % (to - from + 1) + from;
    }

    /**
     * 对象转整数
     *
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @return 转换异常返回 0
     */
    public static double toDouble(Object obj) {
        if (obj == null)
            return 0;
        return toDouble(obj.toString(), 0.0);
    }

    /**
     * 字符串转整数
     */
    public static double toDouble(String str, double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 保留两位小数并四舍五入
     */
    public static float formatPoint(double money) {
        return Float.parseFloat(new DecimalFormat("0.00").format(money));
    }
}
