package com.hihonor.adsdk.demo.external.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class LogUtil {
    private static final String COMMON_TAG_ = "ADS-DEMO-";

    public static void info(@NonNull String tag, @NonNull String msg, @Nullable Object... args) {
        Log.i(getTag(tag), createMessage(msg, args));
    }

    public static void warn(@NonNull String tag, @NonNull String msg, @Nullable Object... args) {
        Log.w(getTag(tag), createMessage(msg, args));
    }

    public static void debug(@NonNull String tag, @NonNull String msg, @Nullable Object... args) {
        Log.d(getTag(tag), createMessage(msg, args));
    }

    public static void debug(@NonNull String tag, @Nullable Object object) {
        Log.d(getTag(tag), toString(object));
    }

    public static void error(@NonNull String tag, @NonNull String msg, @Nullable Object... args) {
        Log.e(getTag(tag), createMessage(msg, args));
    }

    private static String getTag(String tag) {
        return COMMON_TAG_ + tag;
    }

    @NonNull
    private static String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }
}
