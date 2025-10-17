/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.app.Activity;

/**
 * 上下文工具类
 *
 * @author zw0089744
 * @since 2024-04-28
 */
public class ContextUtils {
    /**
     * Activity是否可用
     *
     * @param activity 传入的activity页面
     * @return true 可以，false不可以
     */
    public static boolean isActivityEnable(Activity activity) {
        if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
            return false;
        }
        return true;
    }


}
