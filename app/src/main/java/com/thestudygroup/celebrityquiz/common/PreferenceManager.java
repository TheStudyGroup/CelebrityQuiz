package com.thestudygroup.celebrityquiz.common;

import android.content.Context;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferenceManager
{
    public  static final String  PREFERENCES_NAME      = "preference";
    private static final String  DEFAULT_VALUE_STRING  = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int     DEFAULT_VALUE_INT     = -1;
    private static final long    DEFAULT_VALUE_LONG    = -1L;
    private static final float   DEFAULT_VALUE_FLOAT   = -1F;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static int     getInt    (@NonNull final Context context, @NonNull final String key) { return getInt(context, key, DEFAULT_VALUE_INT);         }
    public static long    getLong   (@NonNull final Context context, @NonNull final String key) { return getLong(context, key, DEFAULT_VALUE_LONG);       }
    public static float   getFloat  (@NonNull final Context context, @NonNull final String key) { return getFloat(context, key, DEFAULT_VALUE_FLOAT);     }
    public static String  getString (@NonNull final Context context, @NonNull final String key) { return getString(context, key, DEFAULT_VALUE_STRING);   }
    public static boolean getBoolean(@NonNull final Context context, @NonNull final String key) { return getBoolean(context, key, DEFAULT_VALUE_BOOLEAN); }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getInt(key, defaultValue);
    }
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getLong(key, defaultValue);
    }
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getFloat(key, defaultValue);
    }
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key, defaultValue);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getBoolean(key, defaultValue);
    }



    public static void setInt(Context context, String key, int value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static void setLong(Context context, String key, long value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }
    public static void setFloat(Context context, String key, float value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
    public static void setString(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }



    public static void removeKey(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.apply();
    }

    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
    }
}
