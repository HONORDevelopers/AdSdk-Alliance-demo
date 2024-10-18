package com.hihonor.adsdk.demo.external.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hihonor.adsdk.demo.external.R;

/**
 * 功能描述
 *
 * @since 2023-06-07
 */
public class GlideUtils {

    private static final String TAG = "GlideUtils";

    public static void loadImage(Context context, String imgUrl, ImageView adImageView, int cornerRadius) {
        if (checkIsDestroyed(context)) {
            LogUtil.error(TAG, "loadImage, activity is destroyed");
            return;
        }
        if (cornerRadius <= 0) {
            Glide.with(context).load(imgUrl).addListener(new GlideLoadRequestListener(imgUrl)).into(adImageView);
            return;
        }
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(context.getResources().getColor(R.color.ads_app_magic_color_quaternary));
        defaultDrawable.setCornerRadius(cornerRadius);
        RequestOptions options = new RequestOptions()
                .transform(new RoundedCorners((int) cornerRadius));
        Glide.with(context).load(imgUrl).error(defaultDrawable).addListener(new GlideLoadRequestListener(imgUrl)).apply(options).into(adImageView);
    }

    private static class GlideLoadRequestListener implements RequestListener{

        private final String mImgUrl;

        public GlideLoadRequestListener(String imgUrl) {
            mImgUrl = imgUrl;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            StringBuilder msg = new StringBuilder();
            msg.append("onLoadFailed, ");
            msg.append("failed url is : ");
            msg.append(mImgUrl);
            msg.append(", msg: ");
            if (null != e) {
                msg.append(e.getMessage());
            }
            LogUtil.error(TAG, "loadImage, "+ msg);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            LogUtil.info(TAG, "onResourceReady...");
            return false;
        }
    }

    /**
     * 检查给定的上下文是否所属的 Activity 是否已销毁。
     *
     * @param context 要检查的上下文对象
     * @return true：关联的 Activity 已销毁，false：关联的 Activity 没销毁
     */
    private static boolean checkIsDestroyed(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            // 检查 activity 是否已经销毁，当 isDestroyed 为 true 时，将会引发 Glide 异常，因此需要进行此判断。
            if (activity.isDestroyed()) {
                LogUtil.error(TAG, "checkIsDestroyed, glide load image exception, activity is destroyed");
                return true;
            }
        }
        return false;
    }
}
