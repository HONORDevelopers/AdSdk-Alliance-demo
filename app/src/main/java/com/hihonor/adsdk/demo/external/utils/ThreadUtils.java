/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 提供用于处理线程的实用工具方法。
 *
 * @author caijinfu W0092240
 * @since 2023/12/18
 */
public final class ThreadUtils {
    private static final String TAG = "ThreadUtils";

    /**
     * 线程存活最长时间毫秒
     */
    private static final long KEEP_ALIVE_TIME = 60L;

    /**
     * SubLooper子线程（Handler形式）
     */
    private static volatile Handler SUB_THREAD_HANDLER;

    /**
     * SubLooper子线程
     */
    private static volatile HandlerThread SUB_THREAD;

    /**
     * 创建一个单例线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> new Thread(r, "adsdk-app-demo"));

    /**
     * 主线程的 Handler，用于在主线程上处理任务
     */
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 检查当前线程是否为主线程。
     *
     * @return 如果当前线程是主线程，则返回 true；否则返回 false。
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 获取主线程的 Handler。
     *
     * @return 主线程的 Handler 对象。
     */
    public static Handler getMainHandler() {
        return HANDLER;
    }

    /**
     * 在主线程上运行给定的任务。
     *
     * @param runnable 要在主线程上运行的任务
     */
    public static void runOnUiThread(@NonNull final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // 如果当前线程是主线程，直接运行任务
            runnable.run();
        } else {
            // 如果不是主线程，通过 Handler 在主线程上执行任务
            HANDLER.post(runnable);
        }
    }

    /**
     * 执行耗时任务
     *
     * @param task Runnable
     */
    public static synchronized void excuteTask(@NonNull Runnable task) {
        EXECUTOR_SERVICE.execute(task);
    }

    /**
     * 主线程执行任务
     *
     * @param runnable 任务
     */
    public static void executeMainThreadTask(Runnable runnable) {
        runOnUiThreadDelayed(runnable, 0L);
    }

    /**
     * 在主线程上延迟一段时间后运行给定的任务。
     *
     * @param runnable    要延迟执行的任务
     * @param delayMillis 延迟的时间（毫秒）
     */
    public static void runOnUiThreadDelayed(@NonNull final Runnable runnable, long delayMillis) {
        // 通过 Handler 在主线程上延迟执行任务
        HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * 根据给定的条件，在特定的线程上执行给定的任务。
     *
     * @param isMainThread 标志，指示是否应该在主线程上执行任务
     * @param runnable     要执行的任务
     */
    public static void executeOnSpecificThread(boolean isMainThread, @NonNull Runnable runnable) {
        if (isMainThread) {
            // 如果是主线程，使用 ThreadUtils.runOnUiThread() 方法在主线程上执行任务
            ThreadUtils.runOnUiThread(runnable);
        } else {
            // 如果不是主线程，创建新的单线程执行器并在其上执行任务
            EXECUTOR_SERVICE.execute(runnable);
        }
    }
}
