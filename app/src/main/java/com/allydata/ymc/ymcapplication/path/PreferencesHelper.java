package com.allydata.ymc.ymcapplication.path;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class PreferencesHelper {
    private static Context context;
    private static SharedPreferences sp;
    public static String PRE_NAME;//preferences目录 PRE_YMC

    private PreferencesHelper(Context context, String prename) {
        sp = context.getSharedPreferences(prename, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(Context context, String name) {
        PreferencesHelper.context = context;
        PreferencesHelper.PRE_NAME = name;
        return PreferencesHolder.instance;
    }

    private static class PreferencesHolder {
        public final static PreferencesHelper instance = new PreferencesHelper(
                context, PRE_NAME);
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    public void setFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public void setLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet(key, null);
    }

    public void setStringSet(String key, Set<String> value) {
        sp.edit().putStringSet(key, value).commit();
    }
}
