/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2025. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hihonor.adsdk.common.image.HonorAdImageDataSource;
import com.hihonor.adsdk.common.image.HonorAdImageLoadCallback;
import com.hihonor.adsdk.common.image.HonorAdImageLoadOptions;
import com.hihonor.adsdk.common.image.HonorAdImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

/**
 * Picasso 的 HonorAdImageLoader 实现
 *
 * @author caijinfu W0092240
 * @since 2025/11/28
 */
@Keep
public class PicassoHonorAdImageLoader implements HonorAdImageLoader {

    private static final String TAG = "PicassoHonorAdImageLoaderDemo";

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView target,
        @Nullable HonorAdImageLoadOptions options, @Nullable HonorAdImageLoadCallback callback) {
        Picasso picasso = Picasso.with(context);
        RequestCreator requestCreator = picasso.load(url);
        if (options != null) {
            // 占位图、错误图
            if (options.getPlaceholderDrawable() != null) {
                requestCreator.placeholder(options.getPlaceholderDrawable());
            }
            if (options.getErrorDrawable() != null) {
                requestCreator.error(options.getErrorDrawable());
            }
            // 尺寸相关
            boolean hasSize = options.getWidth() > 0 && options.getHeight() > 0;
            if (hasSize) {
                requestCreator.resize(options.getWidth(), options.getHeight());
                // 只有在已经 resize 的前提下才允许使用 centerCrop / centerInside
                if (target.getScaleType() != null) {
                    ImageView.ScaleType scaleType = target.getScaleType();
                    if (scaleType == ImageView.ScaleType.CENTER_CROP) {
                        requestCreator.centerCrop();
                    } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE ||
                        scaleType == ImageView.ScaleType.FIT_CENTER ||
                        scaleType == ImageView.ScaleType.FIT_XY ||
                        scaleType == ImageView.ScaleType.FIT_START ||
                        scaleType == ImageView.ScaleType.FIT_END) {
                        requestCreator.centerInside();
                    }
                }
            }
            // 圆角：支持全部圆角和部分圆角
            int cornerRadius = options.getCornerRadius();
            Boolean[] partCornerRadius = options.getPartCornerRadius();
            if (cornerRadius > 0 || partCornerRadius != null) {
                if (partCornerRadius == null) {
                    requestCreator.transform(new RoundedCornersTransform(cornerRadius));
                } else {
                    requestCreator.transform(new RoundedCornersTransform(cornerRadius, partCornerRadius));
                }
            }
            // 缓存策略
            if (!options.isUseMemoryCache()) {
                requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            if (!options.isUseDiskCache()) {
                requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
            }
        }
        // 这里改成使用 Target，这样可以拿到 LoadedFrom
        PicassoTarget picassoTarget = new PicassoTarget(target, url, callback);
        // 为了避免 Target 被 GC，需要在某处持有强引用。
        // 简单做法：挂在 ImageView 的 tag 上（如果已有 tag 使用，建议改用 setTag(int, Object)）
        target.setTag(picassoTarget);
        requestCreator.into(picassoTarget);
    }

    @Override
    public void preload(@NonNull Context context, @NonNull String url,
        @Nullable HonorAdImageLoadOptions options, @Nullable HonorAdImageLoadCallback callback) {
        Picasso picasso = Picasso.with(context);
        RequestCreator requestCreator = picasso.load(url);
        if (options != null) {
            // 缓存策略
            if (!options.isUseMemoryCache()) {
                requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            if (!options.isUseDiskCache()) {
                requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
            }
        }
        requestCreator.fetch(new Callback() {
            @Override
            public void onSuccess() {
                LogUtil.info(TAG, "Picasso preload success");
                if (callback != null) {
                    callback.onSuccess(url, HonorAdImageDataSource.REMOTE);
                }
            }

            @Override
            public void onError() {
                LogUtil.error(TAG, "Picasso preload failed");
                if (callback != null) {
                    callback.onError(url, new RuntimeException("Picasso preload failed"));
                }
            }
        });
    }

    /**
     * 将 Picasso 的图片加载来源映射为 HonorAdImageDataSource 类型
     *
     * @param from Picasso 的图片加载来源
     * @return 对应的 HonorAdImageDataSource 类型值
     */
    private int mapLoadedFromToDataSource(@Nullable Picasso.LoadedFrom from) {
        // 如果来源为空，默认返回远程来源
        if (from == null) {
            return HonorAdImageDataSource.REMOTE;
        }
        // 根据不同的加载来源返回对应的数据源类型
        switch (from) {
            case MEMORY:
                // 内存缓存来源
                return HonorAdImageDataSource.MEMORY_CACHE;
            case DISK:
                // Picasso 只有一个 disk cache，这里归为 RESOURCE_DISK_CACHE
                return HonorAdImageDataSource.RESOURCE_DISK_CACHE;
            case NETWORK:
            default:
                // 网络来源或默认情况返回远程来源
                return HonorAdImageDataSource.REMOTE;
        }
    }

    /**
     * 内部 Target，既负责把 Bitmap 设置到 ImageView，又把 LoadedFrom 转成需要的 dataSource
     */
    private class PicassoTarget implements Target {

        private final WeakReference<ImageView> imageViewRef;

        private final String url;

        private final HonorAdImageLoadCallback callback;

        private PicassoTarget(@NonNull ImageView imageView, @NonNull String url,
            @Nullable HonorAdImageLoadCallback callback) {
            this.imageViewRef = new WeakReference<>(imageView);
            this.url = url;
            this.callback = callback;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ImageView imageView = imageViewRef.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            if (callback != null) {
                int dataSource = mapLoadedFromToDataSource(from);
                callback.onSuccess(url, dataSource);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ImageView imageView = imageViewRef.get();
            if (imageView != null && errorDrawable != null) {
                imageView.setImageDrawable(errorDrawable);
            }
            if (callback != null) {
                callback.onError(url, new RuntimeException("Picasso load failed"));
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            ImageView imageView = imageViewRef.get();
            if (imageView != null && placeHolderDrawable != null) {
                imageView.setImageDrawable(placeHolderDrawable);
            }
        }

    }

}