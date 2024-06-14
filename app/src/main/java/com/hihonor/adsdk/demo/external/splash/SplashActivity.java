/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.HnAds;
import com.hihonor.adsdk.base.api.splash.SplashAdLoadListener;
import com.hihonor.adsdk.base.api.splash.SplashExpressAd;
import com.hihonor.adsdk.base.callback.AdListener;
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
                .setSplashAdLoadListener(new AdLoadListener())
                .setAdSlot(adSlot)
                .build();
        load.loadAd();
    }

    private class AdLoadListener implements SplashAdLoadListener {

        @Override
        public void onLoadSuccess(SplashExpressAd splashExpressAd) {
            LogUtil.info(TAG, "onLoadSuccess, ad load success");
            splashExpressAd.setAdListener(new MyAdListener());
        }

        /**
         * 广告加载失败
         *
         * @param code 错误码
         * @param errorMsg 错误提示信息
         */
        @Override
        public void onFailed(String code, String errorMsg) {
            LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
        }
    }

    /**
     * 广告事件监听器
     */
    private class MyAdListener extends AdListener {

        /**
         * 广告曝光时回调
         */
        @Override
        public void onAdImpression() {
            super.onAdImpression();
            LogUtil.info(TAG, "onAdImpression...");
            Toast.makeText(SplashActivity.this,
                    getString(R.string.ad_impression_success), Toast.LENGTH_SHORT).show();
        }

        /**
         * 广告曝光失败时回调
         *
         * @param msg 曝光失败信息
         */
        @Override
        public void onAdImpressionFailed(int errCode, String msg) {
            super.onAdImpressionFailed(errCode, msg);
            LogUtil.info(TAG, "onAdImpressionFailed, msg: " + msg);
            Toast.makeText(SplashActivity.this, getString(R.string.ad_impression_failed), Toast.LENGTH_SHORT).show();
        }

        /**
         * 广告被点击时回调
         */
        @Override
        public void onAdClicked() {
            super.onAdClicked();
            LogUtil.info(TAG, "onAdClicked...");
            Toast.makeText(SplashActivity.this,
                    getString(R.string.ad_clicked), Toast.LENGTH_SHORT).show();
        }

        /**
         * 广告关闭时回调
         */
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            LogUtil.info(TAG, "onAdClosed...");
            Toast.makeText(SplashActivity.this,
                    getString(R.string.app_ad_close_tip), Toast.LENGTH_SHORT).show();
        }

        /**
         * 开屏广告点击跳过或倒计时结束时回调
         *
         * @param type 0：点击跳过、1：倒计时结束
         */
        @Override
        public void onAdSkip(int type) {
            super.onAdSkip(type);
            LogUtil.info(TAG, "onAdSkip, type: " + type);
            Toast.makeText(SplashActivity.this,
                    getString(R.string.ad_skip), Toast.LENGTH_SHORT).show();
        }

        /**
         * 广告成功跳转小程序时回调
         */
        @Override
        public void onMiniAppStarted() {
            super.onMiniAppStarted();
            LogUtil.info(TAG, "onMiniAppStarted...");
            Toast.makeText(SplashActivity.this,
                    getString(R.string.miniapp_start), Toast.LENGTH_SHORT).show();
        }
    }
}
