/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.splash.SplashAdLoadListener;
import com.hihonor.adsdk.base.api.splash.SplashExpressAd;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.MainActivity;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.AdActionListener;
import com.hihonor.adsdk.demo.external.common.IWeak;
import com.hihonor.adsdk.demo.external.common.WeakLoad;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.SPTools;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;
import com.hihonor.adsdk.splash.SplashAdLoad;

import java.lang.ref.WeakReference;

public class SplashDefaultActivity extends Activity implements IWeak<SplashExpressAd> {

    private static final String TAG = "SplashDefaultActivity";

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.splash_1080_1620_unit_id);

    private long mTimeOut = 0;

    private FrameLayout mRootView;

    /**
     * 广告对象
     */
    private SplashExpressAd mSplashExpressAd;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        int buttonId = intent.getIntExtra(SplashActivity.BUTTON, R.id.bt_load_ad_1);
        switch (buttonId) {
            case R.id.bt_load_ad_0:
                mSlotId = DemoApplication.getAdUnitId(R.string.splash_1080_1920_unit_id);
                break;
            case R.id.bt_load_ad_1:
                mSlotId = DemoApplication.getAdUnitId(R.string.splash_1080_1620_unit_id);
                break;
            case R.id.bt_load_ad_2:
                mSlotId = DemoApplication.getAdUnitId(R.string.splash_720_1280_unit_id);
                break;
        }
        ScreenUtils.transparentStatusBar(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_default);
        initView();
    }

    public void initView() {
        mRootView = findViewById(R.id.root_view);
        obtainAd();
    }

    /**
     * 获取广告
     */
    public void obtainAd() {
        // step1：创建广告请求参数对象（AdSlot）。
        AdSlot.Builder builder = new AdSlot.Builder()
                .setSlotId(mSlotId) // 必填，设置广告位ID。
                .setLoadType(0); // 设置媒体请求Type，目前仅为开屏广告使用。0：普通加载方式，会首先去读缓存； 1 ：预缓存加载，将数据保存至缓存；
        // -1：默认请求方式，表示直接进行网络请求，数据不保存缓存
        if (mTimeOut > 0) {
            builder.setTimeOutMillis(mTimeOut);
        }
        boolean isSupport = SPTools.getAdPrefInstance().getBoolean(SplashActivity.I_SWITCH, false);
        LogUtil.info(TAG, "isSupport: " + isSupport);
        if (isSupport) {
            builder.setAdContext("{\"shakeSwitch\":1}");
        } else {
            builder.setAdContext("{\"shakeSwitch\":2}");
        }
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        SplashAdLoad load = new SplashAdLoad.Builder()
                .setSplashAdLoadListener(new SplashLoadListener(this)) // 必填，注册广告加载状态监听器。
                .setAdSlot(builder.build()) // 必填，设置广告请求参数。
                .build();
        // step5：加载广告
        load.loadAd();
    }

    public void startHomeActivity() {
        // 倒计时结束或者点击跳过
        Intent intent = new Intent(SplashDefaultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * step2：实现广告加载状态监听器，加载过程中获取广告的状态变化。
     * <br>
     * 广告加载状态监听器
     */
    private static class SplashLoadListener extends WeakLoad<SplashExpressAd,
            IWeak<SplashExpressAd>> implements SplashAdLoadListener {
        public SplashLoadListener(IWeak<SplashExpressAd> item) {
            super(item);
        }
    }

    /**
     * 加载成功回调
     *
     * @param splashExpressAd 广告数据
     */
    @Override
    public void onAdLoaded(SplashExpressAd splashExpressAd) {
        LogUtil.info(TAG, "onLoadSuccess, ad load success");
        mSplashExpressAd = splashExpressAd;
        if (mRootView == null) {
            LogUtil.error(TAG, "onLoadSuccess, mRootView is null");
            return;
        }
        LogUtil.info(TAG, "onLoadSuccess isCachedData = " + mSplashExpressAd.isCachedData());
        View splashView = splashExpressAd.getExpressAdView();
        splashExpressAd.setLogoResId(R.drawable.ic_launcher_background);
        splashExpressAd.setMediaNameString(getString(R.string.app_market));
        splashExpressAd.setMediaCopyrightResId(R.string.app_copy_right);
        // 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
        splashExpressAd.setAdListener(new MyAdListener(SplashDefaultActivity.this));
        // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
        // 注意： addView前需要把添加广告的容器rootView将控件上所有的view调用removeAllViews方法移除。
        mRootView.removeAllViews();
        // 添加view，进行广告展示
        mRootView.addView(splashView);
    }

    /**
     * 加载失败回调
     *
     * @param code     code
     * @param errorMsg errorMsg
     */
    @Override
    public void onFailed(String code, String errorMsg) {
        ToastUtils.showShortToast("onFailed: code: " + code + ", errorMsg: " + errorMsg);
        LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
        Intent intent = new Intent(SplashDefaultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private static class MyAdListener extends AdActionListener {
        private final WeakReference<SplashDefaultActivity> reference;

        public MyAdListener(SplashDefaultActivity activity) {
            super("【SplashDefault】");
            reference = new WeakReference<>(activity);
        }

        @Override
        public void onAdSkip(int type) {
            super.onAdSkip(type);
            SplashDefaultActivity activity = reference.get();
            if (activity != null) {
                activity.startHomeActivity();
            }
            reference.clear();
        }
    }

    /**
     * 页面不可见时需要销毁广告
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAd();
    }

    /**
     * 销毁广告
     */
    private void releaseAd() {
        if (mRootView != null) {
            mRootView.removeAllViews();
        }
        if (mSplashExpressAd != null) {
            LogUtil.info(TAG, "releaseAd...");
            mSplashExpressAd.release();
        }
    }
}
