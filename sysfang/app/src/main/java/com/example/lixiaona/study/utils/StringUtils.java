package com.example.lixiaona.study.utils;

import java.util.Random;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 获取随机字符串
     *
     * @param len 字符串的长度
     */
    public static String getRandomString(int len) {
        String returnStr;
        char[] ch = new char[len];
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            ch[i] = (char) (rd.nextInt(9) + 97);
        }
        returnStr = new String(ch);


        return returnStr;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

}
