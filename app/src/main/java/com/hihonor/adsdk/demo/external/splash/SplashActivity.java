/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.HnAds;
import com.hihonor.adsdk.base.api.splash.SplashAdLoadListener;
import com.hihonor.adsdk.base.api.splash.SplashExpressAd;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.splash.SplashAdLoad;

public class SplashActivity extends BaseActivity {
    private final static String TAG = "SplashActivityTAG";

    public static final String BUTTON = "buttonId";

    /**
     * 广告位ID
     */
    private final String mSlotId = DemoApplication.getAdUnitId(R.string.splash_1080_1620_unit_id);

    private TextView preCacheText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_splash_ad));
        setContentView(R.layout.activity_splash);
        initView();
    }

    public void initView() {
        findViewById(R.id.bt_load_ad_0).setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, SplashDefaultActivity.class);
            intent.putExtra(BUTTON, R.id.bt_load_ad_0);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.bt_load_ad_1).setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, SplashDefaultActivity.class);
            intent.putExtra(BUTTON, R.id.bt_load_ad_1);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.bt_load_ad_2).setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, SplashDefaultActivity.class);
            intent.putExtra(BUTTON, R.id.bt_load_ad_2);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.bt_load_ad_3).setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, LandscapeSplashActivity.class);
            intent.putExtra(BUTTON, R.id.bt_load_ad_3);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.bt_load_ad_pre_cache).setOnClickListener(view -> {
            obtainAd();
        });

        findViewById(R.id.bt_query_ad_pre_cache_count).setOnClickListener(view -> {
            int adCacheCount = HnAds.get().getAdManager().getAdCacheCount(mSlotId);
            LogUtil.info(TAG, "adCacheCount = " + adCacheCount);
            preCacheText = findViewById(R.id.ads_cache_size);
            preCacheText.setText(String.valueOf(adCacheCount));
        });
    }

    private void obtainAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setSlotId(mSlotId)
                .setLoadType(Constants.AD_LOAD_TYPE.PRECACHE_REQUEST)
                .build();
        SplashAdLoad load = new SplashAdLoad.Builder()
                .setSplashAdLoadListener(mAdLoadListener)
                .setAdSlot(adSlot)
                .build();
        load.loadAd();
    }

    /**
     * step2：实现广告加载状态监听器，加载过程中获取广告的状态变化。
     * <br>
     * 广告加载状态监听器
     */
    private final SplashAdLoadListener mAdLoadListener = new SplashAdLoadListener() {
        /**
         * 广告加载失败
         *
         * @param code     错误码
         * @param errorMsg 错误提示信息
         */
        @Override
        public void onFailed(String code, String errorMsg) {
            LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
        }

        /**
         * 广告加载成功
         *
         * @param data 广告数据
         */
        @Override
        public void onLoadSuccess(SplashExpressAd data) {
            LogUtil.info(TAG, "onLoadSuccess, ad load success");
        }
    };
}
