package com.hihonor.adsdk.demo.external.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hihonor.adsdk.common.image.HonorAdImageLoadCallback;
import com.hihonor.adsdk.common.image.HonorAdImageLoadOptions;
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
        boolean isSupportImageLoader = SPTools.getAdPrefInstance().getBoolean(Constants.SP_SUPPORT_SDK_IMAGE_LOADER);
        if (cornerRadius <= 0) {
            if (!isSupportImageLoader){
                Glide.with(context).load(imgUrl).addListener(new GlideLoadRequestListener(imgUrl)).into(adImageView);
                return;
            }
            HonorAdImageLoadOptions imageLoadOptions = buildMediaLoadOptions(null, 0);
            PicassoHonorAdImageLoader adImageLoaderDemo = new PicassoHonorAdImageLoader();
            adImageLoaderDemo.loadImage(context, imgUrl, adImageView, imageLoadOptions,
                    new PicassoImageLoadCallback());
            return;
        }
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(context.getResources().getColor(R.color.ads_app_magic_color_quaternary));
        defaultDrawable.setCornerRadius(cornerRadius);
        if (!isSupportImageLoader) {
            RequestOptions options = new RequestOptions()
                    .transform(new RoundedCorners((int) cornerRadius));
            Glide.with(context).load(imgUrl).error(defaultDrawable).listener(new GlideLoadRequestListener(imgUrl)).apply(options).into(adImageView);
            return;
        }
        HonorAdImageLoadOptions imageLoadOptions =
                buildMediaLoadOptions(defaultDrawable, cornerRadius);
        PicassoHonorAdImageLoader adImageLoaderDemo = new PicassoHonorAdImageLoader();
        adImageLoaderDemo.loadImage(context, imgUrl, adImageView, imageLoadOptions,
                new PicassoImageLoadCallback());
    }

    private static void logImageLoadSuccess(String imageLoaderType, Object value,
            Object dataSource) {
        LogUtil.info(TAG, "onResourceReady, " + imageLoaderType
                + ", model = " + value + ", dataSource = " + dataSource);
    }

    private static void logImageLoadError(String imageLoaderType, Object value,
            Throwable throwable) {
        String errorMsg = "";
        if (throwable != null && throwable.getMessage() != null) {
            errorMsg = throwable.getMessage();
        }
        LogUtil.error(TAG, "onLoadFailed, demo glide load image fail msg = "
                + errorMsg + ", " + imageLoaderType + ", model = " + value);
    }

    @NonNull
    private static HonorAdImageLoadOptions buildMediaLoadOptions(Drawable errorDrawable,
            @IntRange(from = 0) int cornerRadius) {
        return new HonorAdImageLoadOptions.Builder().setUseMemoryCache(true).setUseDiskCache(true)
                .setErrorDrawable(errorDrawable).setCornerRadius(cornerRadius).setPartCornerRadius(null)
                .build();
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
            logImageLoadError("glide", model, e);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            LogUtil.info(TAG, "onResourceReady...");
            logImageLoadSuccess("glide", model, dataSource);
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

    /**
     * 封装 PicassoHonorAdImageLoaderDemo 的回调逻辑
     */
    private static class PicassoImageLoadCallback implements HonorAdImageLoadCallback {

        @Override
        public void onSuccess(@NonNull String url, int dataSource) {
            logImageLoadSuccess("picasso", url, dataSource);
        }

        @Override
        public void onError(@NonNull String url, @NonNull Throwable throwable) {
            logImageLoadError("picasso", url, throwable);
        }

    }
}
