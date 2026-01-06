/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.base.NegativeFeedback;
import com.hihonor.adsdk.base.api.CustomVideo;
import com.hihonor.adsdk.base.api.feed.AdDislike;
import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.widget.download.HnDownloadButton;
import com.hihonor.adsdk.common.video.OnVideoPlayListener;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.picturetext.bean.PictureMediaDataBean;
import com.hihonor.adsdk.demo.external.utils.GlideUtils;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.VideoAdViewFactory;
import com.hihonor.adsdk.demo.external.widget.CustomVideoView;
import com.hihonor.adsdk.picturetextad.PictureTextAdRootView;

/**
 * 类描述
 *
 * @author zw0089744
 * @since 2024/11/20
 */
public class AdCustomVideoHolder extends RecyclerView.ViewHolder {
    private final String LOG_TAG = "AdCustomVideoHolder";
    private PictureTextAdRootView mPictureTextAdRootView;
    private CustomVideoView videoView;
    private TextView titleView;
    private TextView brandView;
    private TextView timeText;
    private ImageView logoView;
    private HnDownloadButton mDownLoadButton;
    private ImageView ivMute;
    private boolean isMute;
    private TextView mAdFlagCloseView;
    private FrameLayout finishLayout;
    private TextView tvReplay;
    private ImageView ivCover;
    private Context mContext;

    public AdCustomVideoHolder(@NonNull View itemView) {
        super(itemView);
        mPictureTextAdRootView = itemView.findViewById(R.id.ad_root_view);
        videoView = itemView.findViewById(R.id.video_view);
        titleView = itemView.findViewById(R.id.title_view);
        brandView = itemView.findViewById(R.id.brand_view);
        timeText = itemView.findViewById(R.id.time_text);
        logoView = itemView.findViewById(R.id.logo_view);
        ivMute = itemView.findViewById(R.id.iv_mute);
        finishLayout = itemView.findViewById(R.id.finish_layout);
        tvReplay = itemView.findViewById(R.id.tv_replay);
        mDownLoadButton = itemView.findViewById(R.id.ad_download);
        ivCover = itemView.findViewById(R.id.iv_cover);
        mAdFlagCloseView = itemView.findViewById(R.id.ad_close_view);
    }

    public void bindData(PictureMediaDataBean pictureMediaDataBean) {
        this.mContext = mPictureTextAdRootView.getContext();
        PictureTextExpressAd expressAd = pictureMediaDataBean.getExpressAd();
        mPictureTextAdRootView.setAd(expressAd);
        titleView.setText(expressAd.getTitle() + "-custom-video");
        brandView.setText(expressAd.getTitle());
        GlideUtils.loadImage(DemoApplication.getContext(), expressAd.getLogo(), logoView, 0);
        GlideUtils.loadImage(DemoApplication.getContext(), expressAd.getCoverUrl(), ivCover, 0);
        mDownLoadButton.setBaseAd(expressAd);
        CustomVideo customVideo = expressAd.getCustomVideo();
        isMute = false;
        ivMute.setImageResource(R.drawable.ic_app_volume_off);
        // 设置静音点击事件
        ivMute.setOnClickListener(v -> {
            isMute = !isMute;
            if (isMute) {
                ivMute.setImageResource(R.drawable.ic_app_volume_on);
            } else {
                ivMute.setImageResource(R.drawable.ic_app_volume_off);
            }
            try {
                videoView.setMute(isMute);
            } catch (Exception e) {
                LogUtil.error(LOG_TAG, e.getMessage());
            }

        });
        tvReplay.setOnClickListener(v -> {
            // 重播
            if (customVideo != null) {
                videoView.setVideoURI(Uri.parse(customVideo.getVideoUrl()));
            }
        });
        // 自渲染负反馈
        mAdFlagCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeedNegativeBack(pictureMediaDataBean, mContext);
            }
        });

        if(customVideo != null) {
            videoView.setCustomVideo(customVideo);
            videoView.setVideoURI(Uri.parse(customVideo.getVideoUrl()));
            videoView.setOnVideoPlayListener(new OnVideoPlayListener() {
                @Override
                public void onVideoStart() {
                    super.onVideoStart();
                    videoView.setMute(isMute);
                    finishLayout.setVisibility(View.GONE);
                }

                @Override
                public void onProgressUpdate(long position, long bufferedPosition, long duration) {
                    super.onProgressUpdate(position, bufferedPosition, duration);
                    LogUtil.info(LOG_TAG, "---->onProgressUpdate");
                    String updateTimeText = VideoAdViewFactory.updateTimeText(position, duration);
                    timeText.setText(updateTimeText);
                    ivCover.setVisibility(View.GONE);
                }

                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                    ViewGroup.LayoutParams layoutParams = finishLayout.getLayoutParams();
                    layoutParams.width = videoView.getWidth();
                    layoutParams.height = videoView.getHeight();
                    finishLayout.setLayoutParams(layoutParams);
                    finishLayout.setVisibility(View.VISIBLE);
                    ivCover.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void showFeedNegativeBack(PictureMediaDataBean pictureMediaDataBean, Context context) {
        final PictureTextExpressAd data = pictureMediaDataBean.getExpressAd();
        if (data == null) {
            return;
        }
        final AdDislike adDislike = data.getAdDislike();
        if (adDislike == null) {
            return;
        }
        final String[] items = {"不感兴趣", "内容质量差", "重复相似内容", "直接关闭", "取消"};
        AlertDialog alertDialog =
                new AlertDialog.Builder(context).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                adDislike.onSelected(position, NegativeFeedback.NOT_INTERESTED, true);
                                videoView.stopPlayback();
                                ((ViewGroup) itemView).removeAllViews();
                                break;
                            case 1:
                                adDislike.onSelected(position, NegativeFeedback.POOR_QUALITY_CONTENT, true);
                                videoView.stopPlayback();
                                ((ViewGroup) itemView).removeAllViews();
                                break;
                            case 2:
                                adDislike.onSelected(position, NegativeFeedback.NOT_INTERESTED, false);
                                break;
                            case 3:
                                adDislike.onSelected(position, NegativeFeedback.POOR_CANCEL, true);
                                videoView.stopPlayback();
                                ((ViewGroup) itemView).removeAllViews();
                                break;
                            default:
                                adDislike.onCancel();
                                break;
                        }
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
        adDislike.onShow();
    }
}
