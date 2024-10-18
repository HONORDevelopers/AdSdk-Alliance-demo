package com.hihonor.adsdk.demo.external.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.reward.RewardAdLoadListener;
import com.hihonor.adsdk.base.api.reward.RewardExpressAd;
import com.hihonor.adsdk.base.api.reward.RewardItem;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.AdActionListener;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.common.IWeak;
import com.hihonor.adsdk.demo.external.common.WeakLoad;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;
import com.hihonor.adsdk.reward.RewardAdLoad;

import java.lang.ref.WeakReference;

/**
 * 激励视频界面
 *
 * @author zw0089744
 */
public class RewardActivity extends BaseActivity implements IWeak<RewardExpressAd> {

    private static final String TAG = "RewardActivity";

    private TextView mTvMsg;

    private Button mBtnRewardContent;
    private Switch mAdSwitch;

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.reward_720_1280_land_unit_id);

    private String mRewardName = "Q点券";

    private double mRewardAmount = 1000;

    /**
     * 广告对象
     */
    private RewardExpressAd mRewardExpressAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_reward_ad));
        setContentView(R.layout.activity_reward);
        initView();
    }

    private void initView() {
        mTvMsg = findViewById(R.id.tv_msg);
        mBtnRewardContent = findViewById(R.id.btn_reward_content);
        mBtnRewardContent.setVisibility(View.INVISIBLE);
        mAdSwitch = findViewById(R.id.ad_switch);
        findViewById(R.id.btn_reward_landscape_1280_720).setOnClickListener(view -> {
            mSlotId = DemoApplication.getAdUnitId(R.string.reward_1280_720_land_unit_id);
            obtainAd();
        });
        findViewById(R.id.btn_reward_portrait_1280_720).setOnClickListener(view -> {
            mSlotId = DemoApplication.getAdUnitId(R.string.reward_1280_720_portrait_unit_id);
            obtainAd();
        });
        findViewById(R.id.btn_reward_landscape_720_1280).setOnClickListener(view -> {
            mSlotId = DemoApplication.getAdUnitId(R.string.reward_720_1280_land_unit_id);
            obtainAd();
        });
        findViewById(R.id.btn_reward_portrait_720_1280).setOnClickListener(view -> {
            mSlotId = DemoApplication.getAdUnitId(R.string.reward_720_1280_portrait_unit_id);
            obtainAd();
        });
    }

    /**
     * 获取广告
     */
    private void obtainAd() {
        // step1：创建广告请求参数对象（AdSlot）。
        AdSlot adSlot = new AdSlot.Builder()
                .setSlotId(mSlotId) // 必填，设置广告位ID。
                .setRewardAmount(mRewardAmount) // 设置激励视频广告奖励数量
                .setRewardName(mRewardName) // 设置激励视频广告奖励名称
                .setPreCacheVideo(mAdSwitch.isChecked())
                .build();
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        RewardAdLoad load = new RewardAdLoad.Builder()
                .setRewardAdLoadListener(new RewardLoadListener(this)) // 必填，注册广告加载状态监听器。
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
    private static class RewardLoadListener extends WeakLoad<RewardExpressAd, IWeak<RewardExpressAd>>
            implements RewardAdLoadListener {
        public RewardLoadListener(IWeak<RewardExpressAd> item) {
            super(item);
        }
    }

    /**
     * 广告加载成功回调
     *
     * @param rewardExpressAd 激励广告模板接口基类
     */
    @Override
    public void onAdLoaded(RewardExpressAd rewardExpressAd) {
        mRewardExpressAd = rewardExpressAd;
        LogUtil.info(TAG, "onLoadSuccess, ad load success");
        // 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
        rewardExpressAd.setAdListener(new MyAdListener(mRewardExpressAd));
        // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
        rewardExpressAd.show(RewardActivity.this, new RewardStatusListener(this));
    }

    /**
     * 广告加载失败
     *
     * @param code     错误码
     * @param errorMsg 错误提示信息
     */
    @Override
    public void onFailed(String code, String errorMsg) {
        LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
        mTvMsg.setText(getString(R.string.wrong_msg) + code + " " + errorMsg);
        mTvMsg.setVisibility(View.VISIBLE);
    }

    private static class MyAdListener extends AdActionListener {
        private final WeakReference<RewardExpressAd> mWeakReference;

        MyAdListener(RewardExpressAd rewardExpressAd) {
            super(TAG);
            mWeakReference = new WeakReference<>(rewardExpressAd);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            RewardExpressAd expressAd = mWeakReference.get();
            if (expressAd != null) {
                expressAd.release();
            }
        }
    }

    private static class RewardStatusListener implements RewardExpressAd.RewardAdStatusListener {
        private final WeakReference<RewardActivity> activityWeakReference;

        private RewardStatusListener(RewardActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onRewardAdClosed() {

        }

        @Override
        public void onVideoError(int errorCode) {
            LogUtil.info(TAG, "onRewardAdFailedToShow,errorCode: " + errorCode);
        }


        @Override
        public void onRewardAdOpened() {
            LogUtil.info(TAG, "onRewardAdOpened");
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {
            LogUtil.info(TAG,
                    "onRewarded, 成功获得奖励, amount: " + rewardItem.getAmount() + ", type: " + rewardItem.getType());
            RewardActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onAdRewarded(rewardItem);
            }
        }
    }

    private void onAdRewarded(RewardItem rewardItem) {
        mBtnRewardContent.setText("GetReward: " + rewardItem.getAmount() + " " + rewardItem.getType());
        mBtnRewardContent.setVisibility(View.VISIBLE);
    }

    /**
     * 页面不可见时需要销毁广告
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRewardExpressAd != null) {
            LogUtil.info(TAG, "releaseAd...");
            mRewardExpressAd.release();
        }
    }
}
