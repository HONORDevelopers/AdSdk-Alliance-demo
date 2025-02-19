/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

/*
 * Copyright (c) Honor Device Co., Ltd. 2021-2024. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.picturetext.bean;

import com.hihonor.adsdk.base.api.feed.PictureTextExpressAd;

import java.io.Serializable;

/**
 * 竞价数据
 *
 * @author zw0089744
 * @since 2024/11/12
 */
public class BiddingData implements Serializable {
    private long winPrice;
    private long highestLossPrice;
    private String winPkg;
    private PictureTextExpressAd expressAd;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public PictureTextExpressAd getExpressAd() {
        return expressAd;
    }

    public void setExpressAd(PictureTextExpressAd expressAd) {
        this.expressAd = expressAd;
    }

    public String getWinPkg() {
        return winPkg;
    }

    public void setWinPkg(String winPkg) {
        this.winPkg = winPkg;
    }

    public long getHighestLossPrice() {
        return highestLossPrice;
    }

    public void setHighestLossPrice(long highestLossPrice) {
        this.highestLossPrice = highestLossPrice;
    }

    public long getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(long winPrice) {
        this.winPrice = winPrice;
    }

    @Override
    public String toString() {
        return "BiddingData{" +
                "winPrice=" + winPrice +
                ", highestLossPrice=" + highestLossPrice +
                ", winPkg='" + winPkg + '\'' +
                ", expressAd=" + expressAd +
                ", tag='" + tag + '\'' +
                '}';
    }
}
