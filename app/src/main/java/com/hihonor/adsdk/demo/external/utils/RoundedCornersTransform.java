/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2025. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.Nullable;

import com.squareup.picasso.Transformation;

/**
 * Picasso 图片圆角转换类，支持部分圆角
 *
 * @author caijinfu W0092240
 * @since 2025/11/28
 */
public class RoundedCornersTransform implements Transformation {

    private final float radius;

    /**
     * 顺序：leftTop, rightTop, rightBottom, leftBottom
     */
    private final boolean[] corners = new boolean[]{true, true, true, true};

    /**
     * 全部圆角
     *
     * @param radiusPx 圆角半径，单位：px
     */
    public RoundedCornersTransform(float radiusPx) {
        this(radiusPx, null);
    }

    /**
     * 部分圆角
     *
     * @param radiusPx 圆角半径，单位：px
     * @param partCorners Boolean[4]，顺序：leftTop, rightTop, rightBottom, leftBottom
     */
    public RoundedCornersTransform(float radiusPx, @Nullable Boolean[] partCorners) {
        this.radius = radiusPx;
        if (partCorners != null && partCorners.length == 4) {
            // 做一下 null & 长度保护
            for (int i = 0; i < 4; i++) {
                Boolean c = partCorners[i];
                this.corners[i] = (c != null && c);
            }
        }
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rect = new RectF(0f, 0f, width, height);
        // radii 数组：8 个值，依次是
        // leftTopX, leftTopY, rightTopX, rightTopY,
        // rightBottomX, rightBottomY, leftBottomX, leftBottomY
        float[] radii = new float[8];
        // leftTop
        if (corners[0]) {
            radii[0] = radius;
            radii[1] = radius;
        }
        // rightTop
        if (corners[1]) {
            radii[2] = radius;
            radii[3] = radius;
        }
        // rightBottom
        if (corners[2]) {
            radii[4] = radius;
            radii[5] = radius;
        }
        // leftBottom
        if (corners[3]) {
            radii[6] = radius;
            radii[7] = radius;
        }
        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
        // 回收原图
        if (!source.isRecycled()) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        // key 里要把 radius 和每个角的配置都带上，否则缓存会冲突
        return "rounded_corners_radius_" + radius + "_corners_" + corners[0] + "_" + corners[1] +
            "_" + corners[2] + "_" + corners[3];
    }

}