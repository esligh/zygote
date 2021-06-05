package com.yunzhu.module.common.security;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.yunzhu.module.common.base.BaseApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author: lisc
 * @date: 2020-04-29 上午 11:26
 * @desc: 安全类的SharePreference，基于Androidx的Security库
 * require version : Android M
 */
public class SafePreferenceUtil {

    public static final String PREFERENCE_NAME = "safe_config";

    private static volatile SharedPreferences sInstance;

    public static SharedPreferences getInstance() {
        if (null == sInstance) {
            synchronized (SafePreferenceUtil.class) {
                if (null == sInstance) {
                    try {
                        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                        sInstance = EncryptedSharedPreferences.create(
                                PREFERENCE_NAME,
                                masterKeyAlias,
                                BaseApplication.Companion.getInstance(),
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                        );
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sInstance;
    }

    public static String getString (String key, String defaultStr)
    {
        return getString(PREFERENCE_NAME,key,defaultStr);
    }

    public static String getString(String preferencesName, String key, String defaultStr) {
        SharedPreferences settings = getInstance();
        return settings.getString(key, defaultStr);
    }

    public static void putString(String key, String value)
    {
        putString(PREFERENCE_NAME,key,value);
    }

    public static void putString(String preferencesName, String key, String value)
    {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void removeKey(String key)
    {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * put int preferences
     *
     * @param key
     *            The name of the preference to modify
     * @param value
     *            The new value for the preference
     */
    public static void putInt(String key, int value) {
        putInt(PREFERENCE_NAME, key, value);
    }

    public static void putInt(String preferencesName, String key, int value) {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * get int preferences
     *
     * @param
     * @param key
     *            The name of the preference to retrieve
     * @param defaultValue
     *            Value to return if this preference does not exist
     */
    public static int getInt(String key, int defaultValue) {
        return getInt( PREFERENCE_NAME, key, defaultValue);
    }

    public static int getInt(String preferencesName, String key, int defaultValue) {
        SharedPreferences settings = getInstance();
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param
     * @param key
     *            The name of the preference to modify
     * @param value
     *            The new value for the preference
     */
    public static void putLong(String key, long value) {
        putLong( PREFERENCE_NAME, key, value);
    }

    public static void putLong(String preferencesName, String key, long value) {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * get long preferences
     *
     * @param
     * @param key
     *            The name of the preference to retrieve
     * @param defaultValue
     *            Value to return if this preference does not exist
     */
    public static long getLong(String key, long defaultValue) {
        return getLong( PREFERENCE_NAME, key, defaultValue);
    }

    public static long getLong(String preferencesName, String key, long defaultValue) {
        SharedPreferences settings = getInstance();
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param
     * @param key
     *            The name of the preference to modify
     * @param value
     *            The new value for the preference
     */
    public static void putFloat(String key, float value) {
        putFloat( PREFERENCE_NAME, key, value);
    }

    public static void putFloat(String preferencesName, String key, float value) {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * get float preferences
     *
     * @param
     * @param key
     *            The name of the preference to retrieve
     * @param defaultValue
     *            Value to return if this preference does not exist
     */
    public static float getFloat(String key, float defaultValue) {
        return getFloat( PREFERENCE_NAME, key, defaultValue);
    }

    public static float getFloat(String preferencesName, String key, float defaultValue) {
        SharedPreferences settings = getInstance();
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param
     * @param key
     *            The name of the preference to modify
     * @param value
     *            The new value for the preference
     */
    public static void putBoolean(String key, boolean value) {
        putBoolean(PREFERENCE_NAME, key, value);
    }

    public static void putBoolean(String preferencesName, String key, boolean value) {
        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * get boolean preferences
     *
     * @param
     * @param key
     *            The name of the preference to retrieve
     * @param defaultValue
     *            Value to return if this preference does not exist
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean( PREFERENCE_NAME, key, defaultValue);
    }

    public static boolean getBoolean(String preferencesName, String key, boolean defaultValue) {
        SharedPreferences settings = getInstance();
        return settings.getBoolean(key, defaultValue);
    }



    /**
     * clear the preferences
     *
     * @param context
     * @param
     */
    public static void clear(Context context) {
        clear(PREFERENCE_NAME);
    }

    /**
     * clear the preferences
     *
     * @param
     * @param preferenceName
     */
    public static void clear(String preferenceName) {

        SharedPreferences settings = getInstance();
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

}
