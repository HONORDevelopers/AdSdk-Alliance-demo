/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.base.widget.download.HnDownloadButton;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;

/**
 * 功能描述
 *
 * @since 2023-11-21
 */
public class AppViewHolder extends BaseViewHolder{
    private String TAG = "AppViewHolder";
    private static final int DP_VALUE = 4;
    private final ImageView adImageView;

    private final HnDownloadButton adDownloadView;
    private final TextView adTitle, adContent;
    public AppViewHolder(@NonNull View itemView) {
        super(itemView);
        adImageView = mRootView.findViewById(R.id.ad_image);
        adDownloadView = mRootView.findViewById(R.id.ad_download);
        adTitle = mRootView.findViewById(R.id.ad_title);
        adContent = mRootView.findViewById(R.id.ad_content);
        adFlagView = mRootView.findViewById(R.id.ad_flag_view);
        adFlagCloseView = mRootView.findViewById(R.id.ad_close_view);
    }

    @Override
    public void bindData(@NonNull PictureTextExpressAd baseAd) {
        super.bindData(baseAd);
        if (baseAd == null) {
            LogUtil.error(TAG, "baseAd is null");
            return;
        }
        Context context = mRootView.getContext();
        pictureTextAdRootView.setAd(baseAd);
        setPictureImage(baseAd);
        // 应用下载广告的主标题对应的字段是品牌名称，副标题对应的字段是广告标题
        adTitle.setText(baseAd.getBrand());
        adContent.setText(baseAd.getTitle());

        adFlagView.setRectCornerRadius(ScreenUtils.dpToPx(DP_VALUE));
        adFlagView.setViewPadding(ScreenUtils.dpToPx(DP_VALUE), 0, ScreenUtils.dpToPx(DP_VALUE), 0);
        adFlagView.setVisibility(baseAd.getAdFlag() == 0 ? View.GONE : View.VISIBLE);
        // 广告标签背景色适配深色模式，备注：UX未在参数表中找到对应的资源id，无法使用uikit资源直接适配，需手动判断。
        int color = ScreenUtils.isDarkTheme() ? 0x33FFFFFF : 0x33000000;
        adFlagView.setBgColor(color);

        adFlagCloseView.setVisibility(baseAd.getCloseFlag() == 0 ? View.GONE : View.VISIBLE);
        adFlagCloseView.setBgColor(context.getResources().getColor(R.color.ads_app_magic_color_bg_translucent));
        adFlagCloseView.setViewPadding(0, 0, 0, 0);
        Drawable closeIconDrawable = context.getDrawable(R.drawable.ads_icsvg_public_cancel_regular);
        adFlagCloseView.setCloseIconDrawable(closeIconDrawable);
        adFlagCloseView.setDislikeItemClickListener(new DislikeItemClickListener() {
            @Override
            public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                if (itemView instanceof ViewGroup) {
                    ((ViewGroup) itemView).removeAllViews();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onShow() {

            }
        });
        pictureTextAdRootView.setAdCloseView(adFlagCloseView);
        pictureTextAdRootView.setDislikeItemClickListener(new DislikeItemClickListener() {
            @Override
            public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                pictureTextAdRootView.removeAllViews();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onShow() {

            }
        });
        adDownloadView.setBaseAd(baseAd, Constants.SCENE_TYPE.AD_DEFAULT);
    }

    private void setPictureImage(PictureTextExpressAd baseAd) {
        mRootView.post(()->{
            Context context = mRootView.getContext();
            // 加载图片
            loadImage(context, (PictureTextExpressAd) baseAd, baseAd.getImages()
                    , 0, adImageView, (int) cornerRadius);
        });
    }
}
