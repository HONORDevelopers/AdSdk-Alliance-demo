package com.hihonor.adsdk.demo.external.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;

import java.util.List;

/**
 * 功能描述
 *
 * @since 2023-11-21
 */
public class ViewUtils {
    private static final String LOG_TAG = "ViewUtils";
    public static void setViewHeight(View target, int height) {
        LogUtil.info(LOG_TAG, "Call set view height.");
        if (target == null) {
            LogUtil.warn(LOG_TAG, "target view is null!");
            return;
        }
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        layoutParams.height = height;
        target.setLayoutParams(layoutParams);

    }
    public static void setViewWidth(View target, int width) {
        LogUtil.info(LOG_TAG, "Call set view width.");
        if (target == null) {
            LogUtil.warn(LOG_TAG, "target view is null!");
            return;
        }
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        layoutParams.width = width;
        target.setLayoutParams(layoutParams);
    }

    public static void loadImage(Context context, PictureTextExpressAd baseAd, List<String> images, int position, ImageView adImageView, int cornerRadius) {
        LogUtil.info(LOG_TAG, "Call load image.");
        if (baseAd == null || adImageView == null) {
            LogUtil.warn(LOG_TAG, "baseAd or adImageView is null!");
            return;
        }
        String imgUrl = null;
        if (images != null && images.size() > position) {
            imgUrl = images.get(position);
        }
        GlideUtils.loadImage(context, imgUrl, adImageView, cornerRadius);
    }
}
