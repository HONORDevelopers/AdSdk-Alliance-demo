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
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;

/**
 * 功能描述
 *
 * @since 2023-11-21
 */
public class SmallAdViewHolder extends BaseViewHolder {
    private static final String LOG_TAG = "SmallAdViewHolder";
    private final ImageView adImageView;

    public SmallAdViewHolder(@NonNull View itemView) {
        super(itemView);
        adImageView = findViewById(R.id.ad_image);
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
            imgHeight = DensityUtil.px2dip(mRootView.getContext(), 150);
        }
        setViewHeight(adImageView, imgHeight);
        // 渲染文本视图 标题/品牌名/落地按钮文本/关闭按钮等
        renderTextView(baseAd);
        Context context = mRootView.getContext();
        // 获取图片比例，如果获取不到，则小图默认是3:2
        float proportion = (3f / 2f);
        // 根布局左padding边距
        float paddingMaxStart = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_start);
        // 根布局左padding边距
        float paddingMaxEnd = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_end);
        // 三图每个图片的间距
        float horizontalImagePadding = context.getResources()
                .getDimension(R.dimen.ads_app_magic_dimens_element_horizontal_middle);
        // 小图文图片宽度计算 (父布局宽度 - 父布局距离两边边距 - （3图图片之间的间距）) / 3（这里3的意思是按照3图的比例计算的宽度）
        int imageWidth = (int) (ScreenUtils.getScreenWidth() - paddingMaxStart - paddingMaxEnd
                - (horizontalImagePadding * 2)) / 3;

        int imageHeight = (int) (imageWidth / proportion);
        setViewWidth(adImageView, imageWidth);
        setViewHeight(adImageView, imageHeight);
        // 加载图片
        loadImage(context, baseAd, baseAd.getImages(), 0, adImageView, (int) cornerRadius);
    }
}
