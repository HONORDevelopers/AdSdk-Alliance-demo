/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.utils.DensityUtil;
import com.hihonor.adsdk.demo.external.utils.GlideUtils;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;
import com.hihonor.adsdk.demo.external.utils.ViewUtils;

/**
 * 功能描述
 *
 * @since 2023-11-21
 */
public class BigPictureAdViewHolder extends BaseViewHolder {
    private static final String LOG_TAG = "PictureBaseViewHolder";

    protected ImageView brandLogoView;

    private ImageView adImageView;

    public BigPictureAdViewHolder(@NonNull View itemView) {
        super(itemView);
        adImageView = itemView.findViewById(R.id.ad_image);
        brandLogoView = itemView.findViewById(R.id.ad_brand_logo);
        brandLogoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindData(@NonNull PictureTextExpressAd baseAd) {
        super.bindData(baseAd);
        // 这里设置默认图片高度是防止广告被连续加入以RecyclerView为列表的容器中时，初始图片未加载时高度为0，
        // 导致本来不应该展示在屏幕的item，在刚addView时展示了一瞬间（速度很快，但是用户看不到）但实际从代码角度看
        // 是展示了的，实际这些item应该是在屏幕外的，这种情况会导致多曝光。
        int imgHeight = baseAd.getImgHeight();
        if (imgHeight == 0) {
            LogUtil.warn(LOG_TAG, "server res image height is zero!");
            imgHeight = (int) (DensityUtil.getScreenWidth(mRootView.getContext()) / (16f / 9f));
        }
        ViewUtils.setViewHeight(adImageView, imgHeight);
        int radius = 0;
        if (baseAd.getStyle() != null) {
            radius = baseAd.getStyle().getBorderRadius();
        }
        // 渲染logo
        GlideUtils.loadImage(mRootView.getContext(), baseAd.getLogo(), brandLogoView, radius);
        //渲染图片
        Context context = mRootView.getContext();
        // 获取图片比例，如果获取不到，则大图默认是16:9
        float proportion = (16f / 9f);
        // 大图根布局左padding边距
        float paddingMaxStart = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_start);
        // 大图根布局右padding边距
        float paddingMaxEnd = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_end);
        int paddingHorLength = (int) (paddingMaxStart + paddingMaxEnd);
        // 图片宽度 = 父控件测量宽度 - 左右边距。
        int width = ScreenUtils.getScreenWidth() - paddingHorLength;
        ViewUtils.setViewWidth(adImageView, width);
        ViewUtils.setViewHeight(adImageView, (int) (width / proportion));

        ViewUtils.setViewWidth(adBrandAutoSizeLayout, width);
        ViewUtils.setViewWidth(titleView, width);
        // 加载图片
        ViewUtils.loadImage(context, baseAd, baseAd.getImages(), 0, adImageView, (int) cornerRadius);
    }
}
