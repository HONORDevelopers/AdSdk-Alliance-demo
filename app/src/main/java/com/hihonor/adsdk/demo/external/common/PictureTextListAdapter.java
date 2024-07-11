package com.hihonor.adsdk.demo.external.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.demo.external.R;

import java.util.List;

/**
 * 功能描述
 *
 * @since 2022-12-09
 */
public class PictureTextListAdapter extends RecyclerView.Adapter<PictureTextListAdapter.PictureTextListViewHolder>{
    private static final String LOG_TAG = PictureTextListAdapter.class.getSimpleName();
    private List<PictureTextExpressAd> mPictureTextExpressAdList;

    public PictureTextListAdapter(List<PictureTextExpressAd> mPictureTextExpressAdList) {
        this.mPictureTextExpressAdList = mPictureTextExpressAdList;
    }

    @NonNull
    @Override
    public PictureTextListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_picture_text_item, null, false);
        return new PictureTextListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureTextListViewHolder holder, int position) {
        holder.bindData(mPictureTextExpressAdList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mPictureTextExpressAdList != null) ? mPictureTextExpressAdList.size() : 0;
    }

    static class PictureTextListViewHolder extends RecyclerView.ViewHolder {

        public PictureTextListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(PictureTextExpressAd pictureTextExpressAd) {
            ViewGroup viewGroup = (ViewGroup) itemView;
            viewGroup.removeAllViews();
            // 获取广告视图
            View expressAdView = pictureTextExpressAd.getExpressAdView();
            // 检查广告视图是否已经有父视图，如果有则移除
            if (expressAdView.getParent() instanceof ViewGroup) {
                ((ViewGroup) expressAdView.getParent()).removeAllViews();
            }
            // 添加view，进行广告展示
            // 注意事项：必须添加广告view，否则不会展示广告
            viewGroup.addView(expressAdView);
            // 设置广告负反馈回调监听器
            pictureTextExpressAd.setDislikeClickListener(new DislikeItemClickListener() {
                @Override
                public void onFeedItemClick(int i, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                    // 移除广告视图
                    viewGroup.removeView(expressAdView);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onShow() {

                }
            });
        }

    }

}
