/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2023. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.base.widget.base.AdFlagCloseView;
import com.hihonor.adsdk.base.widget.download.HnDownloadButton;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.utils.GlideUtils;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;
import com.hihonor.adsdk.picturetextad.PictureTextAdRootView;

import java.util.List;

/**
 * 功能描述
 *
 * @since 2023-11-21
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private static final String LOG_TAG = "BaseViewHolder";
    protected View mRootView;
    protected RelativeLayout adBrandAutoSizeLayout;
    protected TextView brandNameTextView;
    protected HnDownloadButton downLoadButton;
    protected float cornerRadius;
    protected TextView titleView;
    protected AdFlagCloseView adFlagView;
    protected AdFlagCloseView adFlagCloseView;

    protected PictureTextAdRootView pictureTextAdRootView;

    /**
     * 上下文
     */
    protected Context context;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mRootView = itemView;
        adBrandAutoSizeLayout = itemView.findViewById(R.id.ad_auto_size_layout);
        brandNameTextView = itemView.findViewById(R.id.ad_brand_name);
        downLoadButton = itemView.findViewById(R.id.ad_download);
        titleView = itemView.findViewById(R.id.ad_title);
        adFlagView = itemView.findViewById(R.id.ad_flag_view);
        adFlagCloseView = itemView.findViewById(R.id.ad_close_view);
        pictureTextAdRootView = itemView.findViewById(R.id.root_view);
    }

    protected void bindData(@NonNull PictureTextExpressAd baseAd) {
        if (baseAd == null) {
            LogUtil.warn(LOG_TAG, "baseAd is null");
            return;
        }
        this.context = mRootView.getContext();
        adFlagCloseView.setDislikeItemClickListener(new DislikeItemClickListener() {
            @Override
            public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                ViewParent parent = mRootView.getParent();
                if (parent != null) {
                    ((ViewGroup)parent).removeView(mRootView);
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onShow() {

            }
        });
        pictureTextAdRootView.setAd(baseAd);
        pictureTextAdRootView.setAdCloseView(adFlagCloseView);
        renderTextView(baseAd);
    }
    protected  <T extends View> T findViewById(@IdRes int id) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }
    protected void renderTextView(@NonNull PictureTextExpressAd baseAd) {
        if (baseAd == null) {
            return;
        }
        LogUtil.info(LOG_TAG, "render text view, set text data.");
        if (brandNameTextView != null) {
            brandNameTextView.setText(baseAd.getBrand());
        }
        // 设置title
        if (titleView != null) {
            titleView.setText(baseAd.getTitle());
        }
        initAdFlag(baseAd);
        initAdClose(baseAd);
        showDownloadButton(baseAd);
    }

    /**
     * 初始化close按钮
     *
     * @param baseAd baseAd
     */
    private void initAdClose(PictureTextExpressAd baseAd) {
        if (adFlagCloseView == null) {
            LogUtil.warn(LOG_TAG, "adFlagCloseView is null");
            return;
        }
        int closeFlag = baseAd.getCloseFlag();
        adFlagCloseView.setVisibility(closeFlag == 0 ? View.INVISIBLE : View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = downLoadButton.getLayoutParams();
        if (closeFlag == 0) {
            // 隐藏X按钮
            setViewWidth(adFlagCloseView, 0);
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).setMarginEnd(0);
            }
            LogUtil.info(LOG_TAG, "closeFlag is OFF");
        } else {
            if (context == null) {
                LogUtil.warn(LOG_TAG, "initAdClose context is null");
                return;
            }
            // 显示X按钮
            adFlagCloseView.setBgColor(context.getResources().getColor(R.color.ads_app_magic_button_default));
            Drawable closeIconDrawable =
                    context.getDrawable(R.drawable.ic_honor_ads_close_black);
            adFlagCloseView.setCloseIconDrawable(closeIconDrawable);
            float marginEnd =
                    context.getResources().getDimension(R.dimen.ads_app_magic_dimens_element_horizontal_middle);
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).setMarginEnd((int) marginEnd);
            }
            LogUtil.info(LOG_TAG, "closeFlag is ON");
        }
        // 负反馈弹窗点击不喜欢后，移除view
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
    }

    /**
     * 初始化flag
     *
     * @param baseAd baseAd
     */
    private void initAdFlag(PictureTextExpressAd baseAd) {
        if (adFlagCloseView == null) {
            return;
        }
        int adFlag = baseAd.getAdFlag();
        adFlagView.setVisibility(adFlag == 0 ? View.INVISIBLE : View.VISIBLE);
        if (adFlag == 0) {
            setViewWidth(adFlagView, 0);
            setViewHeight(adFlagView, 0);
            ViewGroup.LayoutParams layoutParams = downLoadButton.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams && context != null) {
                float marginStart =
                        context.getResources().getDimension(R.dimen.ads_app_magic_dimens_element_horizontal_middle);
                ((RelativeLayout.LayoutParams) layoutParams).setMarginStart((int) marginStart);
            }
            LogUtil.info(LOG_TAG, "adFlag is OFF");
        } else {
            // 广告标签背景色适配深色模式，备注：UX未在参数表中找到对应的资源id，无法使用uikit资源直接适配，需手动判断。
            int color = ScreenUtils.isDarkTheme() ? 0x33FFFFFF : 0x33000000;
            adFlagView.setBgColor(color);
            LogUtil.info(LOG_TAG, "adFlag is ON");
        }
    }

    /**
     * 初始化downLoadButton
     */
    private void showDownloadButton(PictureTextExpressAd baseAd) {
        if (downLoadButton != null) {
            // 下载类的广告downLoadButton才会显示downLoadButton
            pictureTextAdRootView.setAd(baseAd);
        }
    }

    public void setViewHeight(View target, int height) {
        LogUtil.info(LOG_TAG, "Call set view height.");
        if (target == null) {
            LogUtil.warn(LOG_TAG, "target view is null!");
            return;
        }
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        layoutParams.height = height;
        target.setLayoutParams(layoutParams);

    }
    public void setViewWidth(View target, int width) {
        LogUtil.info(LOG_TAG, "Call set view width.");
        if (target == null) {
            LogUtil.warn(LOG_TAG, "target view is null!");
            return;
        }
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        layoutParams.width = width;
        target.setLayoutParams(layoutParams);
    }

    public void loadImage(Context context, PictureTextExpressAd baseAd, List<String> images, int position, ImageView adImageView, int cornerRadius) {
        LogUtil.info(LOG_TAG, "Call load image.");
        if (baseAd == null || adImageView == null || context == null) {
            LogUtil.warn(LOG_TAG, "baseAd or adImageView or context is null!");
            return;
        }
        String imgUrl = null;
        if (images != null && images.size() > position) {
            imgUrl = images.get(position);
        }
        GlideUtils.loadImage(context, imgUrl, adImageView, cornerRadius);
    }
}
