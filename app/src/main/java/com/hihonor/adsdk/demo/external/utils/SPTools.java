package com.hihonor.adsdk.demo.external.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hihonor.adsdk.demo.external.DemoApplication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * SharedPreferences工具
 */
public class SPTools {
    /**
     * 广告设备信息sp名称
     */
    public static String SP_NAME_HONOR_AD_PREF = "honor_ad_demo_pref";
    private static final ConcurrentMap<String, SPTools> MAP = new ConcurrentHashMap<>();
    public final SharedPreferences sp;

    private SPTools(final String spName) {
        sp = DemoApplication.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * getInstance sp名称honor_ad_pref
     *
     * @return SPTools
     */
    public static SPTools getInstance() {
        return getInstance(SP_NAME_HONOR_AD_PREF);
    }

    /**
     * getInstance sp名称honor_ad_pref
     *
     * @return SPTools
     */
    public static SPTools getAdPrefInstance() {
        return getInstance(SP_NAME_HONOR_AD_PREF);
    }

    public static SPTools getInstance(String spName) {
        String name = spName;
        if (TextUtils.isEmpty(name)) {
            name = SP_NAME_HONOR_AD_PREF;
        }
        SPTools spTools = MAP.get(name);
        if (spTools == null) {
            synchronized (SPTools.class) {
                if (spTools == null) {
                    spTools = new SPTools(name);
                    MAP.put(name, spTools);
                }
            }
        }
        return spTools;
    }

    public void putString(@NonNull final String key, @NonNull final String value) {
        putString(key, value, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putString(@NonNull final String key, @NonNull final String value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putInt(@NonNull final String key, final int value) {
        putInt(key, value, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putInt(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(@NonNull final String key, final long value) {
        putLong(key, value, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putLong(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putFloat(@NonNull final String key, final float value) {
        putFloat(key, value, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putFloat(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(@NonNull final String key, final boolean value) {
        putBoolean(key, value, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putBoolean(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void putStringSet(@NonNull final String key, @NonNull final Set<String> values) {
        putStringSet(key, values, false);
    }

    @SuppressLint("ApplySharedPref")
    public void putStringSet(@NonNull final String key, @NonNull final Set<String> values, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, values).commit();
        } else {
            sp.edit().putStringSet(key, values).apply();
        }
    }

    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.emptySet());
    }

    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValue) {
        Set<String> set = sp.getStringSet(key, defaultValue);
        if (set == null) {
            return new HashSet<>();
        } else {
            return new HashSet<>(set);
        }
    }

    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    @SuppressLint("ApplySharedPref")
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    public void clear() {
        clear(false);
    }

    @SuppressLint("ApplySharedPref")
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

}