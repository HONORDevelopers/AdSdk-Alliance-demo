/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.hihonor.adsdk.demo.external.DemoApplication;

/**
 * ToastUtil 类包含一组方法用于显示 Toast 消息。
 *
 * @author caijinfu W0092240
 * @since 2023/12/29
 */
public class ToastUtils {
    private static final String TAG = "ToastUtil";

    /**
     * Toast 消息显示的时间间隔阈值，单位为毫秒。
     */
    private static final int TOAST_INTERVAL_TIME = 1000;

    /**
     * 上次显示 Toast 消息的时间戳。
     */
    private static long OLD_TIME = -1L;

    /**
     * 显示短时长的 Toast 消息，使用字符串作为消息内容。
     *
     * @param message 要显示的字符串消息
     */
    public static void showShortToast(@NonNull String message) {
        realShowToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时长的 Toast 消息，使用字符串作为消息内容。
     *
     * @param message 要显示的字符串消息
     */
    public static void showLongToast(@NonNull String message) {
        realShowToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 显示短时长的 Toast 消息，使用资源 ID 作为消息内容。
     *
     * @param messageId 要显示的消息的资源 ID
     */
    public static void showShortToast(@StringRes int messageId) {
        realShowToast(messageId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时长的 Toast 消息，使用资源 ID 作为消息内容。
     *
     * @param messageId 要显示的消息的资源 ID
     */
    public static void showLongToast(@StringRes int messageId) {
        realShowToast(messageId, Toast.LENGTH_LONG);
    }

    /**
     * 防止重复显示的toast
     *
     * @param messageId messageId
     */
    public static void showOnlyShortToast(@StringRes int messageId) {
        long curTime = System.currentTimeMillis();
        if (curTime - OLD_TIME > TOAST_INTERVAL_TIME) {
            OLD_TIME = curTime;
            realShowToast(messageId, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 显示 Toast 消息。
     *
     * @param messageId 要显示的消息的资源 ID
     * @param duration  消息显示的持续时间
     */
    private static void realShowToast(@StringRes int messageId, int duration) {
        Context context = DemoApplication.getContext();
        if (context == null) {
            LogUtil.error(TAG, "realShowToast, context is null, can not be displayed messageId");
            return;
        }
        realShowToast(context.getResources().getText(messageId), duration);
    }

    /**
     * 显示包含文本内容的 Toast 消息。
     *
     * @param text     要显示的文本内容
     * @param duration 消息显示的持续时间
     */
    private static void realShowToast(@NonNull CharSequence text, int duration) {
        Context context = DemoApplication.getContext();
        if (context == null) {
            LogUtil.error(TAG, "realShowToast, context is null, can not be displayed text");
            return;
        }
        if (TextUtils.isEmpty(text)) {
            LogUtil.error(TAG, "realShowToast, text is isEmpty, can not be displayed text");
            return;
        }
        if (ThreadUtils.isMainThread()) {
            Toast.makeText(context, text, duration).show();
            return;
        }
        ThreadUtils.executeMainThreadTask(() -> {
            Toast.makeText(context, text, duration).show();
        });
    }

}
