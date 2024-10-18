/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.common;

import android.annotation.SuppressLint;

import com.hihonor.adsdk.base.callback.AdListener;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;

/**
 * 广告监听
 *
 * @author zw0089744
 * @since 2024/6/21
 */
public class AdActionListener extends AdListener {
    private final String name;

    public AdActionListener(String name) {
        this.name = name;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onAdClosed() {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onAdClosed");
        ToastUtils.showShortToast(R.string.app_ad_close_tip);
    }

    @Override
    public void onAdClicked() {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onAdClicked");
        ToastUtils.showShortToast(R.string.ad_clicked);
    }

    @Override
    public void onAdImpression() {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onAdImpression");
        ToastUtils.showShortToast(R.string.ad_impression_success);
    }

    @Override
    public void onAdImpressionFailed(int errCode, String msg) {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onAdImpressionFailed#msg:" + msg);
        ToastUtils.showShortToast(R.string.ad_impression_failed);
    }

    @Override
    public void onMiniAppStarted() {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onMiniAppStarted");
        ToastUtils.showShortToast(R.string.miniapp_start);
    }

    @Override
    public void onAdSkip(int type) {
        LogUtil.info(Constants.AD_LISTENER_TAG, name + " AdListener#onAdSkip");
        ToastUtils.showShortToast(R.string.ad_skip);
    }
}
