/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext;

import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.feed.PictureTextAdLoadListener;
import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.callback.AdListener;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.picturetext.adapter.PictureTextListSelfRenderAdapter;
import com.hihonor.adsdk.demo.external.picturetext.bean.PictureMediaDataBean;
import com.hihonor.adsdk.demo.external.utils.CollectionUtils;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;
import com.hihonor.adsdk.picturetextad.PictureTextAdLoad;

import java.util.ArrayList;
import java.util.List;

public class PictureTextSelfRenderActivity extends BaseActivity {

    private static final String TAG = "PictureTextSelfTAG";

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.feed_hor_video_unit_id);

    private PictureTextListSelfRenderAdapter mAdapter;

    private RecyclerView mPictureTextRecyclerView;

    private List<PictureMediaDataBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.info(TAG, "onCreate");
        setTitle(getString(R.string.app_picture_text_self_render_ad));
        setContentView(R.layout.activity_picture_text_self_render);
        initView();
    }

    private void initView() {
        LogUtil.info(TAG, "initView");
        mPictureTextRecyclerView = findViewById(R.id.picture_text_self_render_recycler_view);
        Button adLoadButton = findViewById(R.id.bt_load_ad);
        mAdapter = new PictureTextListSelfRenderAdapter();
        mPictureTextRecyclerView.setAdapter(mAdapter);
        mPictureTextRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false)
        );
        adLoadButton.setOnClickListener((v) -> {
            releaseAd();
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
                .setRenderType(1)// 设置渲染类型，0：模板渲染； 1 ：自渲染；
                .build();
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        PictureTextAdLoad load = new PictureTextAdLoad.Builder()
                .setPictureTextAdLoadListener(mAdLoadListener) // 必填，注册广告加载状态监听器。
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
    private final PictureTextAdLoadListener mAdLoadListener = new PictureTextAdLoadListener() {
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
         * 广告加载成功回调。
         *
         * @param adViewList 回调广告数据集合
         */
        @Override
        public void onAdLoaded(List<PictureTextExpressAd> adViewList) {
            LogUtil.info(TAG, "onAdLoaded, ad load success");
            if (CollectionUtils.isEmpty(adViewList)) {
                ToastUtils.showShortToast("Request success but data is empty");
                return;
            }
            mDatas.clear();
            for (int i = 0; i < adViewList.size(); i++) {
                createMediaData(i);
                PictureTextExpressAd expressAd = adViewList.get(i);
                LogUtil.info(TAG, "isHaveTemplateView %s position=%s,： ", expressAd.getExpressAdView(), i);
                expressAd.setDownloadStatusChange(status ->
                        LogUtil.info(TAG, "pkgName=" + expressAd.getAppPackage() + " : status=" + status));
                PictureMediaDataBean pictureMediaDataBean = new PictureMediaDataBean();
                String itemName = expressAd.getRequestId();
                pictureMediaDataBean.setItemName(itemName);
                pictureMediaDataBean.setExpressAd(expressAd);
                pictureMediaDataBean.setPosition(i);
                LogUtil.info(TAG, getString(R.string.application_name) + expressAd.getBrand()
                        + getString(R.string.application_version) + expressAd.getAppVersion()
                        + getString(R.string.developer_name) + expressAd.getDeveloperName()
                        + getString(R.string.permissions_url) + expressAd.getPermissionsUrl()
                        + getString(R.string.privacy_agreement_url) + expressAd.getPrivacyAgreementUrl()
                        + getString(R.string.home_page) + expressAd.getHomePage());
                if (expressAd.hasVideo()) {
                    // 视频广告
                    if (i % 2 != 0) {
                        pictureMediaDataBean.setItemType(Constants.AD_ITEM_TYPE.AD_VIDEO);
                    } else {
                        pictureMediaDataBean.setItemType(Constants.AD_ITEM_TYPE.CUSTOM_VIDEO);
                    }
                } else {
                    // 信息流广告
                    pictureMediaDataBean.setItemType(getItemType(expressAd));
                }
                mDatas.add(pictureMediaDataBean);
                // step2： 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
                expressAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        LogUtil.info(TAG, " AdListener#onAdClicked");
                        ToastUtils.showShortToast(R.string.ad_clicked);
                    }

                    @Override
                    public void onAdImpression() {
                        LogUtil.info(TAG, " AdListener#onAdImpression");
                        ToastUtils.showShortToast(R.string.ad_impression_success);
                    }

                    @Override
                    public void onMiniAppStarted() {
                        LogUtil.info(TAG, " AdListener#onMiniAppStarted");
                        ToastUtils.showShortToast(R.string.miniapp_start);
                    }

                    @Override
                    public void onAdClosed() {
                        LogUtil.info(TAG, " AdListener#onAdClosed");
                        ToastUtils.showShortToast(R.string.app_ad_close_tip);
                    }
                });
            }
            // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
            mAdapter = new PictureTextListSelfRenderAdapter();
            mPictureTextRecyclerView.setAdapter(mAdapter);
            mAdapter.setMediaDataBeanList(mDatas);
            mAdapter.notifyDataSetChanged();
        }
    };

    private int getItemType(PictureTextExpressAd expressAd) {
        // 信息流广告
        int layout = 0;
        if (expressAd.getStyle() != null) {
            layout = expressAd.getStyle().getLayout();
        }
        if (expressAd.getSubType() == Constants.SUB_TYPE.BIG_PICTURE) {
            if (layout == Constants.LAYOUT.TOP_PICTURE_BOTTOM_TEXT) {
                return Constants.AD_ITEM_TYPE.BIG_TOP_PICTURE;
            }
            if (layout == Constants.LAYOUT.TOP_TEXT_BOTTOM_PICTURE) {
                return Constants.AD_ITEM_TYPE.BIG_TOP_TEXT;
            }
        }
        if (expressAd.getSubType() == Constants.SUB_TYPE.THREE_PICTURE) {
            if (layout == Constants.LAYOUT.TOP_PICTURE_BOTTOM_TEXT) {
                return Constants.AD_ITEM_TYPE.THREE_TOP_PICTURE;
            }
            if (layout == Constants.LAYOUT.TOP_TEXT_BOTTOM_PICTURE) {
                return Constants.AD_ITEM_TYPE.THREE_TOP_TEXT;
            }
        }
        if (expressAd.getSubType() == Constants.SUB_TYPE.SMALL_PICTURE) {
            if (layout == Constants.LAYOUT.LEFT_TEXT_RIGHT_PICTURE) {
                return Constants.AD_ITEM_TYPE.SMALL_LEFT_TEXT;
            }
            if (layout == Constants.LAYOUT.RIGHT_TEXT_LEFT_PICTURE) {
                return Constants.AD_ITEM_TYPE.SMALL_LEFT_PICTURE;
            }
        }
        if (expressAd.getSubType() == Constants.SUB_TYPE.APP_PICTURE) {
            return Constants.AD_ITEM_TYPE.APP_PICTURE;
        }
        return Constants.AD_ITEM_TYPE.NORMAL;
    }

    private void createMediaData(int number) {
        for (int i = 0; i < 3; i++) {
            PictureMediaDataBean pictureMediaDataBean = new PictureMediaDataBean();
            String itemName = String.format("Media-Data-item-%s-%s", number, i);
            pictureMediaDataBean.setItemName(itemName);
            pictureMediaDataBean.setPosition(i);
            mDatas.add(pictureMediaDataBean);
        }
    }

    /**
     * 页面不可见时需要销毁广告
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseAd();
    }

    /**
     * 销毁广告
     */
    private void releaseAd() {
        if (CollectionUtils.isNotEmpty(mDatas)) {
            for (PictureMediaDataBean data : mDatas) {
                PictureTextExpressAd expressAd = data.getExpressAd();
                if (expressAd != null) {
                    LogUtil.info(TAG, "releaseAd...");
                    expressAd.release();
                }
            }
        }
    }
}
