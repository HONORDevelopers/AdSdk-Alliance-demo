/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.common;

/**
 * 软引用回调接口 List
 *
 * @author yw0092032
 * @since 2024-04-10
 */
public interface IWeak<E> {
    /**
     * 广告失败回调
     *
     * @param code code
     * @param errorMsg errorMsg
     */
    void onFailed(String code, String errorMsg);

    /**
     * 广告加载成功回调
     *
     * @param data 广告数据
     */
    void onAdLoaded(E data);
}
