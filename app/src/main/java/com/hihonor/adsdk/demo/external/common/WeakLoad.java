/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.common;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 软引用加载
 *
 * @author yw0092032
 * @since 2024-04-10
 */
public abstract class WeakLoad<E, T extends IWeak<E>> implements IWeak<E> {
    private final WeakReference<T> mReference;

    public WeakLoad(T item) {
        mReference = new WeakReference<>(item);
    }

    /**
     * 获取引用的对象
     *
     * @return The object to which this reference refers, or null if this reference object has been cleared
     */
    @Nullable
    protected T get() {
        return mReference.get();
    }

    @Override
    public void onFailed(String code, String errorMsg) {
        T weak = get();
        if (weak != null) {
            weak.onFailed(code, errorMsg);
        }
    }

    @Override
    public void onAdLoaded(E data) {
        T weak = get();
        if(weak != null){
            weak.onAdLoaded(data);
        }
    }

    public void onLoadSuccess(E data) {
        onAdLoaded(data);
    }
}
