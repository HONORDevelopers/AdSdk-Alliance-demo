package com.hihonor.adsdk.demo.external.picturetext.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
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
import com.hihonor.adsdk.demo.external.picturetext.holder.AppViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.BigPictureAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.SmallAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.ThreePictureAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.AdVideoViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.bean.PictureMediaDataBean;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.GlideUtils;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.VideoAdViewFactory;
import com.hihonor.adsdk.demo.external.widget.CustomVideoView;
import com.hihonor.adsdk.picturetextad.PictureTextAdRootView;

import java.util.List;

/**
 * 功能描述
 *
 * @since 2022-12-09
 */
public class PictureTextListSelfRenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String LOG_TAG = "PictureTextListSelfRenderAdapter";
    private List<PictureMediaDataBean> mMediaDataBeanList;

    public void setMediaDataBeanList(List<PictureMediaDataBean> mediaDataBeanList) {
        mMediaDataBeanList = mediaDataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.AD_ITEM_TYPE.NORMAL) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_picture_text_media_item, null, false);
            return new MediaViewHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.AD_VIDEO){
            // 使用广告播放器的视频
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_top_video_bottom_text, null, false);
            return new AdVideoViewHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.CUSTOM_VIDEO){
            // 使用媒体自定义播放器的视频
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_item_custom_video_layout, null, false);
            return new AdCustomVideoHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.BIG_TOP_TEXT) {
            // 大图文上文下图
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_top_text_bottom_picture, null, false);
            return new BigPictureAdViewHolder(rootView);
        }  else if (viewType == Constants.AD_ITEM_TYPE.BIG_TOP_PICTURE) {
            // 大图文上图下文
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_top_picture_bottom_text, null, false);
            return new BigPictureAdViewHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.THREE_TOP_TEXT) {
            // 三图文上文下图
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_three_top_text_bottom_picture, null, false);
            return new ThreePictureAdViewHolder(rootView);
        }  else if (viewType == Constants.AD_ITEM_TYPE.THREE_TOP_PICTURE) {
            // 三图文上图下文
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_three_top_picture_bottom_text, null, false);
            return new ThreePictureAdViewHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.SMALL_LEFT_TEXT) {
            // 小图文左文右图
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_left_text_right_picture, null, false);
            return new SmallAdViewHolder(rootView);
        }  else if (viewType == Constants.AD_ITEM_TYPE.SMALL_LEFT_PICTURE) {
            // 小图文左图右文
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_left_picture_right_text, null, false);
            return new SmallAdViewHolder(rootView);
        } else if (viewType == Constants.AD_ITEM_TYPE.APP_PICTURE) {
            // 应用图文
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_render_left_picture_right_download, null, false);
            return new AppViewHolder(rootView);
        } else {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_picture_text_media_item, null, false);
            return new MediaViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PictureMediaDataBean pictureMediaDataBean = mMediaDataBeanList.get(position);
        if (pictureMediaDataBean == null) {
            return;
        }
        if (holder instanceof MediaViewHolder) {
            // 媒体normal item
            ((MediaViewHolder)holder).bindData(pictureMediaDataBean);
        } else if(holder instanceof  BigPictureAdViewHolder) {
            // 大图文
            ((BigPictureAdViewHolder)holder).bindData(pictureMediaDataBean.getExpressAd());
        }  else if(holder instanceof  ThreePictureAdViewHolder) {
            // 大图文
            ((ThreePictureAdViewHolder)holder).bindData(pictureMediaDataBean.getExpressAd());
        }  else if(holder instanceof  AppViewHolder) {
            // 应用图文
            ((AppViewHolder)holder).bindData(pictureMediaDataBean.getExpressAd());
        } else if (holder instanceof AdVideoViewHolder) {
            // 广告视频播放器
            ((AdVideoViewHolder)holder).bindData(pictureMediaDataBean.getExpressAd());
        } else if (holder instanceof SmallAdViewHolder) {
            // 广告视频播放器
            ((SmallAdViewHolder)holder).bindData(pictureMediaDataBean.getExpressAd());
        } else if (holder instanceof AdCustomVideoHolder) {
            // 媒体自定义播放器
            ((AdCustomVideoHolder)holder).bindData(pictureMediaDataBean);
        }
        LogUtil.info(LOG_TAG, "onBindViewHolder two %s ", pictureMediaDataBean.getItemName());
    }

    @Override
    public int getItemViewType(int position) {
        PictureMediaDataBean pictureMediaDataBean = mMediaDataBeanList.get(position);
        return pictureMediaDataBean.getItemType();
    }

    @Override
    public int getItemCount() {
        return (mMediaDataBeanList != null) ? mMediaDataBeanList.size() : 0;
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_text_view);
        }
        public void bindData(PictureMediaDataBean pictureMediaDataBean) {
            mTextView.setText(pictureMediaDataBean.getItemName());
        }
    }

    static class AdCustomVideoHolder extends RecyclerView.ViewHolder {
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
            GlideUtils.loadImage(DemoApplication.sContext, expressAd.getLogo(), logoView, 0);
            GlideUtils.loadImage(DemoApplication.sContext, expressAd.getCoverUrl(), ivCover, 0);
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
            mAdFlagCloseView.setOnClickListener(v -> {
                    showFeedNegativeBack(pictureMediaDataBean, mContext);
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
                        timeText.setText(updateTimeText(position, duration));
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
            PictureTextExpressAd data = pictureMediaDataBean.getExpressAd();
            int position = pictureMediaDataBean.getPosition();
            AdDislike adDislike = data.getAdDislike();

            String[] items = {"不感兴趣",
                    "质量差",
                    "取消"};
            AlertDialog alertDialog =
                    new AlertDialog.Builder(context).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            if (data == null) {
                                return;
                            }
                            AdDislike adDislike = data.getAdDislike();
                            if (adDislike != null) {
                                if (pos != items.length - 1) {
                                    if (position % 2 == 0) {
                                        // 偶数删除
                                        NegativeFeedback feedback = NegativeFeedback.getNegativeFeedbackByDesc(items[pos]);
                                        adDislike.onSelected(pos, feedback, true);
                                        videoView.stopPlayback();
                                        ((ViewGroup) itemView).removeAllViews();
                                        data.release();
                                    } else {
                                        // 基数不删除
                                        NegativeFeedback feedback = NegativeFeedback.getNegativeFeedbackByDesc(items[pos]);
                                        adDislike.onSelected(pos, feedback, false);
                                    }
                                } else {
                                    adDislike.onCancel();
                                }
                            }
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();
            if (adDislike != null) {
                adDislike.onShow();
            }
        }

        private static final String DEFAULT_MINUTE = "00";
        private static final String TIME_SECTION = ":";

        public String updateTimeText(long position, long duration) {
            String timeText;
            long leftPosition = duration - position;
            if (leftPosition >= (60 * 1000)) {
                long minute = leftPosition / (60 * 1000); // 分
                long second = (leftPosition - minute * 60 * 1000) / 1000; // 秒
                timeText = (minute < 10 ? ("0" + minute) : minute) + TIME_SECTION + (second < 10 ? ("0" + second) : second);
            } else {
                long second = (leftPosition / 1000);
                timeText = DEFAULT_MINUTE + TIME_SECTION + (second < 10 ? ("0" + second) : second);
            }
            return timeText;
        }
    }
}
