package com.example.lixiaona.study.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * SP存储
 */
public class SPUtils {
    public static void clearAll(Context paramContext) {
        getDefaultPreference(paramContext).edit().clear().apply();
    }

    public static void clearAll(SharedPreferences paramSharedPreferences) {
        paramSharedPreferences.edit().clear().apply();
    }

    public static Map<String, ?> getAll(Context paramContext) {
        return getAll(getDefaultPreference(paramContext));
    }

    public static Map<String, ?> getAll(SharedPreferences paramSharedPreferences) {
        if (paramSharedPreferences == null)
            return null;
        return paramSharedPreferences.getAll();
    }

    public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean) {
        return getBoolean(getDefaultPreference(paramContext), paramString, paramBoolean);
    }

    public static boolean getBoolean(SharedPreferences paramSharedPreferences, String paramString, boolean paramBoolean) {
        if (paramSharedPreferences == null)
            return paramBoolean;
        return paramSharedPreferences.getBoolean(paramString, paramBoolean);
    }

    public static SharedPreferences getDefaultPreference(Context paramContext) {
        return getDefaultPreference(paramContext, 0);
    }

    public static SharedPreferences getDefaultPreference(Context paramContext, int paramInt) {
        return getPreference(paramContext, "app_share", paramInt);
    }

    public static float getFloat(Context paramContext, String paramString, float paramFloat) {
        return getFloat(getDefaultPreference(paramContext), paramString, paramFloat);
    }

    public static float getFloat(SharedPreferences paramSharedPreferences, String paramString, float paramFloat) {
        if (paramSharedPreferences == null)
            return paramFloat;
        return paramSharedPreferences.getFloat(paramString, paramFloat);
    }

    public static int getInt(Context paramContext, String paramString, int paramInt) {
        return getInt(getDefaultPreference(paramContext), paramString, paramInt);
    }

    public static int getInt(SharedPreferences paramSharedPreferences, String paramString, int paramInt) {
        if (paramSharedPreferences == null)
            return paramInt;
        return paramSharedPreferences.getInt(paramString, paramInt);
    }

    public static long getLong(Context paramContext, String paramString, long paramLong) {
        return getLong(getDefaultPreference(paramContext), paramString, paramLong);
    }

    public static long getLong(SharedPreferences paramSharedPreferences, String paramString, long paramLong) {
        if (paramSharedPreferences == null)
            return paramLong;
        return paramSharedPreferences.getLong(paramString, paramLong);
    }

    public static SharedPreferences getPreference(Context paramContext, String paramString) {
        return getPreference(paramContext, paramString, 0);
    }

    public static SharedPreferences getPreference(Context paramContext, String paramString, int paramInt) {
        if (paramContext == null)
            return null;
        return paramContext.getSharedPreferences(paramString, paramInt);
    }

    public static String getString(Context paramContext, String paramString1, String paramString2) {
        return getString(getDefaultPreference(paramContext), paramString1, paramString2);
    }

    public static String getString(SharedPreferences paramSharedPreferences, String paramString1, String paramString2) {
        if (paramSharedPreferences == null)
            return null;
        return paramSharedPreferences.getString(paramString1, paramString2);
    }

    public static Set<String> getStringSet(Context paramContext, String paramString, Set<String> paramSet) {
        return getStringSet(getDefaultPreference(paramContext), paramString, paramSet);
    }

    public static Set<String> getStringSet(SharedPreferences paramSharedPreferences, String paramString, Set<String> paramSet) {
        if (paramSharedPreferences == null)
            return paramSet;
        return paramSharedPreferences.getStringSet(paramString, paramSet);
    }

    public static void putBoolean(Context paramContext, String paramString, boolean paramBoolean) {
        putBoolean(getDefaultPreference(paramContext), paramString, paramBoolean);
    }

    public static void putBoolean(SharedPreferences paramSharedPreferences, String paramString, boolean paramBoolean) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putBoolean(paramString, paramBoolean).apply();
    }

    public static void putFloat(Context paramContext, String paramString, float paramFloat) {
        putFloat(getDefaultPreference(paramContext), paramString, paramFloat);
    }

    public static void putFloat(SharedPreferences paramSharedPreferences, String paramString, float paramFloat) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putFloat(paramString, paramFloat).apply();
    }

    public static void putInt(Context paramContext, String paramString, int paramInt) {
        putInt(getDefaultPreference(paramContext), paramString, paramInt);
    }

    public static void putInt(SharedPreferences paramSharedPreferences, String paramString, int paramInt) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putInt(paramString, paramInt).apply();
    }

    public static void putLong(Context paramContext, String paramString, long paramLong) {
        putLong(getDefaultPreference(paramContext), paramString, paramLong);
    }

    public static void putLong(SharedPreferences paramSharedPreferences, String paramString, long paramLong) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putLong(paramString, paramLong).apply();
    }

    public static void putString(Context paramContext, String paramString1, String paramString2) {
        putString(getDefaultPreference(paramContext), paramString1, paramString2);
    }

    public static void putString(SharedPreferences paramSharedPreferences, String paramString1, String paramString2) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putString(paramString1, paramString2).apply();
    }

    public static void putStringSet(Context paramContext, String paramString, Set<String> paramSet) {
        putStringSet(getDefaultPreference(paramContext), paramString, paramSet);
    }

    public static void putStringSet(SharedPreferences paramSharedPreferences, String paramString, Set<String> paramSet) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().putStringSet(paramString, paramSet).apply();
    }

    public static void removeString(Context paramContext, String paramString) {
        removeString(getDefaultPreference(paramContext), paramString);
    }

    public static void removeString(SharedPreferences paramSharedPreferences, String paramString) {
        if (paramSharedPreferences == null)
            return;
        paramSharedPreferences.edit().remove(paramString).apply();
    }
}