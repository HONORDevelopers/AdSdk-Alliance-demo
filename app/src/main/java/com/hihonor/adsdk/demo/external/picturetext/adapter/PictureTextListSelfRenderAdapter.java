package com.hihonor.adsdk.demo.external.picturetext.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.picturetext.holder.AppViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.BigPictureAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.SmallAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.ThreePictureAdViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.AdVideoViewHolder;
import com.hihonor.adsdk.demo.external.picturetext.holder.AdCustomVideoHolder;
import com.hihonor.adsdk.demo.external.picturetext.bean.PictureMediaDataBean;
import com.hihonor.adsdk.demo.external.utils.Constants;
import com.hihonor.adsdk.demo.external.utils.LogUtil;

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
}
