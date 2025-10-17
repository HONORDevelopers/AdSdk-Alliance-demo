/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2022. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * DensityUtil像素相关
 *
 * @since 2022-05-18
 */
public class DensityUtil {
    /**
     * 偏移量
     */
    public static final float DP_DIFF = 0.5f;

    private DensityUtil() {
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px 值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + DP_DIFF);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + DP_DIFF);
    }



    /**
     * sp转换为px
     * @param context  上下文环境
     * @param spValue  需要转换的值
     * @return int
     * @createtime 2022/9/16 14:05
     **/
    public static int sp2px(Context context, int spValue){
        final float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * density + DP_DIFF);
    }

    /**
     * 获取屏幕宽
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        return getMetricsFull(context).widthPixels;
    }

    /**
     * 获取当前屏幕包含状态栏的尺寸大小
     *
     * @param context context
     * @return DisplayMetrics
     */
    private static DisplayMetrics getMetricsFull(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        if (context.getSystemService(Context.WINDOW_SERVICE) instanceof WindowManager) {
            WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowMgr.getDefaultDisplay().getRealMetrics(metrics);
        }
        return metrics;
    }

    /**
     * 获取屏幕高
     *
     * @param context 上下文
     * @return 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        return getMetricsFull(context).heightPixels;
    }

    /**
     * 获取屏幕方向
     *
     * @param context 上下文
     * @return 0竖屏 1横屏
     */
    public static int getScreenOrientation(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return 1;
        } else {
            // 竖屏
            return 0;
        }
    }

    /**
     * 算出请求Banner的比例与实际返回的比例是否在预期范围之内 5%
     *
     * @param requestRatio 请求比例
     * @param returnRatio 云侧返回图实际比例
     * @return 是否在范围内
     */
    public static boolean isRatioAllowed(float requestRatio, float returnRatio) {
        return !(Math.abs((requestRatio - returnRatio) * 100) >= 5);
    }

    /**
     * getdpi
     *
     * @param context 上下文
     * @return dp值
     */
    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

}
