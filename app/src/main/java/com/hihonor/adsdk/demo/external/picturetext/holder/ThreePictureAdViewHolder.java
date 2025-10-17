package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class ThreePictureAdViewHolder extends BaseViewHolder {
    private static final String LOG_TAG = "ThreePictureAdViewHolder";
    private ImageView adImageView01, adImageView02, adImageView03;
    private LinearLayout adThreePictureLayout;

    public ThreePictureAdViewHolder(@NonNull View itemView) {
        super(itemView);
        adThreePictureLayout = findViewById(R.id.ad_three_picture_layout);
        adImageView01 = findViewById(R.id.ad_image);
        adImageView02 = findViewById(R.id.ad_image2);
        adImageView03 = findViewById(R.id.ad_image3);
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
        setViewHeight(adImageView01, imgHeight);
        setViewHeight(adImageView02, imgHeight);
        setViewHeight(adImageView03, imgHeight);
        // 渲染文本视图 标题/品牌名/落地按钮文本/关闭按钮等
        renderTextView(baseAd);
        Context context = mRootView.getContext();
        // 获取图片比例，如果获取不到，则三图默认是3:2
        float proportion = (3f / 2f);
        // 三图根布局左padding边距
        float paddingMaxStart = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_start);
        // 三图根布局左padding边距
        float paddingMaxEnd = context.getResources().getDimension(R.dimen.ads_app_magic_dimens_max_end);
        int paddingHorLength = (int) (paddingMaxStart + paddingMaxEnd);
        // 三图父布局宽度 = 广告布局宽度 - 左右Padding
        int width = ScreenUtils.getScreenWidth() - paddingHorLength;
        setViewWidth(adThreePictureLayout, width);
        // 三图每个图片的间距
        float horizontalImagePadding = context.getResources()
                .getDimension(R.dimen.ads_app_magic_dimens_element_horizontal_middle);
        // 三图父布局宽度 / 3 = 单个图片宽度
        int imageWidth = (int) ((width - (horizontalImagePadding * 2)) / 3);
        int imageHeight = (int) (imageWidth / proportion);
        // 设置图1宽高
        setViewWidth(adImageView01, imageWidth);
        setViewHeight(adImageView01, imageHeight);
        // 设置图2宽高
        setViewWidth(adImageView02, imageWidth);
        setViewHeight(adImageView02, imageHeight);
        // 设置图3宽高
        setViewWidth(adImageView03, imageWidth);
        setViewHeight(adImageView03, imageHeight);

        setViewWidth(titleView, width);
        setViewWidth(adBrandAutoSizeLayout, width);

        // 加载图片
        loadImage(context, baseAd, baseAd.getImages(), 0, adImageView01, (int) cornerRadius);
        loadImage(context, baseAd, baseAd.getImages(), 1, adImageView02, (int) cornerRadius);
        loadImage(context, baseAd, baseAd.getImages(), 2, adImageView03, (int) cornerRadius);

    }
}
