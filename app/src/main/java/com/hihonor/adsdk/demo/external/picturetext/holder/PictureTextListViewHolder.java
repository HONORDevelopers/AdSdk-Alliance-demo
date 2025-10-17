/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.base.api.bid.BiddingLossReason;
import com.hihonor.adsdk.base.api.bid.BiddingSrc;
import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.picturetext.bean.BiddingData;
import com.hihonor.adsdk.demo.external.utils.LogUtil;

/**
 * 类描述
 *
 * @author zw0089744
 * @since 2024/11/18
 */
public class PictureTextListViewHolder extends RecyclerView.ViewHolder {
    private final FrameLayout mPictureTextItemView;
    private final TextView mPictureTextFlagText;

    public PictureTextListViewHolder(@NonNull View itemView) {
        super(itemView);
        mPictureTextItemView = itemView.findViewById(R.id.pic_temp_container_view);
        mPictureTextFlagText = itemView.findViewById(R.id.pic_temp_text);
    }

    public void bindData(BiddingData biddingData) {
        if (biddingData == null) {
            return;
        }
        mPictureTextItemView.removeAllViews();
        // 获取广告视图
        PictureTextExpressAd expressAd = biddingData.getExpressAd();
        int renderMode = expressAd.getRenderMode();
        if (biddingData.getTag() == 0) {
            // 竞价成功
            expressAd.sendWinNotification(biddingData.getWinPrice(), biddingData.getHighestLossPrice());
            View expressAdView = expressAd.getExpressAdView();
            if (renderMode == 1 || expressAdView == null) {
                LogUtil.info("PictureTextListViewHolder", "renderMode is selfRender or expressAdView is null!");
                return;
            }
            // 检查广告视图是否已经有父视图，如果有则移除
            if (expressAdView.getParent() instanceof ViewGroup) {
                ((ViewGroup) expressAdView.getParent()).removeAllViews();
            }
            // 添加view，进行广告展示
            // 注意事项：必须添加广告view，否则不会展示广告
            mPictureTextItemView.addView(expressAdView);
            // 设置广告负反馈回调监听器
            expressAd.setDislikeClickListener(new DislikeItemClickListener() {
                @Override
                public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                    // 移除广告视图
                    mPictureTextItemView.removeView(expressAdView);
                    mPictureTextFlagText.setVisibility(View.GONE);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onShow() {

                }
            });
            mPictureTextFlagText.setText("竞价成功");
        } else if (biddingData.getTag() == 1) {
            // 竞价失败
            expressAd.sendLossNotification(biddingData.getWinPrice(), BiddingLossReason.LOW_PRICE, BiddingSrc.CSJ
                    , biddingData.getWinPkg());
            mPictureTextFlagText.setText("竞价失败");
        }
    }
}
