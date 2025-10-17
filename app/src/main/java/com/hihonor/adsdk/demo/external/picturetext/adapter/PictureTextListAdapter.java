/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.picturetext.bean.BiddingData;
import com.hihonor.adsdk.demo.external.picturetext.holder.PictureTextListViewHolder;

import java.util.List;

/**
 * 信息流模板渲染Adapter
 *
 * @author zw0089744
 * @since 2022-12-09
 */
public class PictureTextListAdapter extends RecyclerView.Adapter<PictureTextListViewHolder> {
    private final List<BiddingData> mPictureTextBiddingDataList;

    public PictureTextListAdapter(List<BiddingData> mPictureTextBiddingDataList) {
        this.mPictureTextBiddingDataList = mPictureTextBiddingDataList;
    }

    @NonNull
    @Override
    public PictureTextListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_picture_text_item, null,
                false);
        return new PictureTextListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureTextListViewHolder holder, int position) {
        holder.bindData(mPictureTextBiddingDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mPictureTextBiddingDataList != null) ? mPictureTextBiddingDataList.size() : 0;
    }

}
