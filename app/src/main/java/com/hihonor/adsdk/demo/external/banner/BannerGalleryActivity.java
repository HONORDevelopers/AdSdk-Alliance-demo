package com.hihonor.adsdk.demo.external.banner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.hihonor.adsdk.banner.api.BannerAdLoad;
import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.banner.BannerAdLoadListener;
import com.hihonor.adsdk.base.api.banner.BannerExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.AdListener;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;

public class BannerGalleryActivity extends BaseActivity {

    private static final String TAG = "BannerGalleryActivity";

    /**
     * 轮播时间
     */
    private final long INTERVAL_TIME = 30000;

    private FrameLayout mRootView;

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.banner_1080_180_unit_id);

    /**
     * 广告View
     */
    private View mExpressAdView;

    /**
     * 广告对象
     */
    private BannerExpressAd mBannerExpressAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_banner_gallery));
        setContentView(R.layout.activity_banner_gallery);
        initView();
    }

    private void initView() {
        Button adLoadButton = findViewById(R.id.bt_load_ad);
        mRootView = findViewById(R.id.ad_content);
        adLoadButton.setOnClickListener(view -> obtainAd());
    }

    /**
     * 获取广告
     */
    private void obtainAd() {
        // step1：创建广告请求参数对象（AdSlot）。
        AdSlot adSlot = new AdSlot.Builder()
                .setSlotId(mSlotId) // 必填，设置广告位ID。
                .setWidth(1280) // 设置广告宽度，目前仅Banner广告使用。
                .setHeight(720) // 设置广告高度，目前仅Banner广告使用。
                .build();
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        BannerAdLoad load = new BannerAdLoad.Builder()
                .setBannerAdLoadListener(mAdLoadListener) // 必填，注册广告加载状态监听器。
                .setAdSlot(adSlot) // 必填，设置广告请求参数。
                .build();
        // step5：加载广告
        load.loadAd();
    }

    /**
     * step2：实现广告加载状态监听器，加载过程中获取广告的状态变化。
     * <br>
     * 广告加载状态监听器
     */
    private final BannerAdLoadListener mAdLoadListener = new BannerAdLoadListener() {
        /**
         * 广告加载失败
         *
         * @param code     错误码
         * @param errorMsg 错误提示信息
         */
        @Override
        public void onFailed(String code, String errorMsg) {
            LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
            ToastUtils.showShortToast("onFailed: code: " + code + ", errorMsg: " + errorMsg);
        }

        /**
         * 广告加载成功回调
         *
         * @param bannerExpressAd banner广告模板接口基类
         */
        @Override
        public void onLoadSuccess(BannerExpressAd bannerExpressAd) {
            mBannerExpressAd = bannerExpressAd;
            LogUtil.info(TAG, "onLoadSuccess, ad load success");
            if (mRootView == null) {
                LogUtil.error(TAG, "onLoadSuccess, mRootView is null");
                return;
            }
            // step2： 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
            bannerExpressAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    LogUtil.info(TAG, " AdListener#onAdClosed");
                    ToastUtils.showShortToast(R.string.app_ad_close_tip);
                    releaseAd();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    LogUtil.info(TAG, " AdListener#onAdClicked");
                    ToastUtils.showShortToast(R.string.ad_clicked);
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    LogUtil.info(TAG, " AdListener#onAdImpression");
                    ToastUtils.showShortToast(R.string.ad_impression_success);
                }

                @Override
                public void onMiniAppStarted() {
                    super.onMiniAppStarted();
                    LogUtil.info(TAG, " AdListener#onMiniAppStarted");
                    ToastUtils.showShortToast(R.string.miniapp_start);
                }
            });
            // 设置广告负反馈回调监听器
            bannerExpressAd.setDislikeClickListener(new DislikeItemClickListener() {
                @Override
                public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                    mRootView.removeAllViews();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onShow() {

                }
            });
            mExpressAdView = bannerExpressAd.getExpressAdView();
            // 设置banner广告轮播时间
            bannerExpressAd.setIntervalTime(INTERVAL_TIME);
            // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
            // 注意： addView前需要把添加广告的容器rootView将控件上所有的view调用removeAllViews方法移除。
            mRootView.removeAllViews();
            // 添加view，进行广告展示
            mRootView.addView(mExpressAdView);
        }
    };

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
        if (mBannerExpressAd != null) {
            LogUtil.info(TAG, "releaseAd...");
            mBannerExpressAd.release();
        }
    }

}
