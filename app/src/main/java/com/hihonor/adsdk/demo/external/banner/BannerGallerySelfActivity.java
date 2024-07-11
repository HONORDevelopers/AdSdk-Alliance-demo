package com.hihonor.adsdk.demo.external.banner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hihonor.adsdk.banner.api.BannerAdLoad;
import com.hihonor.adsdk.banner.api.BannerAdRootView;
import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.banner.BannerAdLoadListener;
import com.hihonor.adsdk.base.api.banner.BannerExpressAd;
import com.hihonor.adsdk.base.bean.DislikeInfo;
import com.hihonor.adsdk.base.callback.DislikeItemClickListener;
import com.hihonor.adsdk.base.widget.base.AdFlagCloseView;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.AdActionListener;
import com.hihonor.adsdk.demo.external.common.BannerPageAdapter;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.common.IWeak;
import com.hihonor.adsdk.demo.external.common.WeakLoad;
import com.hihonor.adsdk.demo.external.utils.CollectionUtils;
import com.hihonor.adsdk.demo.external.utils.GlideUtils;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;
import com.hihonor.adsdk.demo.external.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerGallerySelfActivity extends BaseActivity implements IWeak<BannerExpressAd> {

    private static final String TAG = "BannerGallerySelfActivity";

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.banner_1280_720_unit_id);

    private final static int MEDIA_ITEM_COUNT = 5;

    private final static int AD_POSITION = 3;

    private final String mImgUrl = "https://hshop.honorfile.com/shopdc/pic/202473/0/5cca9166-b56c-49fb-a2f3-1a1a0e913fca.png?https://hshop.honorfile.com/shopdc/pic/202473/0/5cca9166-b56c-49fb-a2f3-1a1a0e913fca.png";

    private MyViewPager mViewPager;

    private View mAdLoadLayout;

    private TextView mTextErrorInfo;

    /**
     * 广告对象
     */
    private BannerExpressAd mBannerExpressAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_banner_gallery_self));
        setContentView(R.layout.activity_banner_gallery_self);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setPageMargin(50);
        mAdLoadLayout = findViewById(R.id.ad_load_layout);
        mTextErrorInfo = findViewById(R.id.text_error_info);
        findViewById(R.id.bt_load_ad).setOnClickListener(v -> {
            mAdLoadLayout.setVisibility(View.VISIBLE);
            obtainAd();
        });
    }

    /**
     * 获取广告
     */
    private void obtainAd() {
        // step1：创建广告请求参数对象（AdSlot）。
        AdSlot adSlot = new AdSlot.Builder()
            .setSlotId(mSlotId) // 必填，设置广告位ID。
            .setWidth(1280) // 设置广告宽度，目前仅Banner广告使用，作为兜底宽度，一般优先使用服务器下发的宽度。
            .setHeight(720) // 设置广告高度，目前仅Banner广告使用，作为兜底高度，一般优先使用服务器下发的高度。
            .setRenderType(1) // 设置渲染类型，0：模板渲染； 1 ：自渲染；
            .build();
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        BannerAdLoad load = new BannerAdLoad.Builder()
            .setBannerAdLoadListener(new BannerLoadListener(this)) // 必填，注册广告加载状态监听器。
            .setAdSlot(adSlot) // 必填，设置广告请求参数。
            .build();
        // step5：加载广告
        load.loadAd();
    }

    /**
     * step2：实现广告加载状态监听器，加载过程中获取广告的状态变化。
     * <br>
     * 广告加载状态监听器
     */
    private static class BannerLoadListener extends WeakLoad<BannerExpressAd,
            IWeak<BannerExpressAd>> implements BannerAdLoadListener {
        public BannerLoadListener(IWeak<BannerExpressAd> item) {
            super(item);
        }
    }

    /**
     * 广告加载失败
     *
     * @param code 错误码
     * @param errorMsg 错误提示信息
     */
    @Override
    public void onFailed(String code, String errorMsg) {
        LogUtil.info(TAG, "onFailed: code: " + code + ", errorMsg: " + errorMsg);
        displayOnError(errorMsg);
        ToastUtils.showShortToast("onFailed: code: " + code + ", errorMsg: " + errorMsg);
    }

    /**
     * 广告加载成功回调
     *
     * @param bannerExpressAd banner广告模板接口基类
     */
    @Override
    public void onAdLoaded(BannerExpressAd bannerExpressAd) {
        mBannerExpressAd = bannerExpressAd;
        LogUtil.info(TAG, "onLoadSuccess, ad load success");
        ArrayList<View> viewList = new ArrayList<>();
        for (int i = 0; i < (MEDIA_ITEM_COUNT + 1); i++) {
            if (AD_POSITION != i) {
                addDefaultView(viewList);
            } else {
                // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
                addAdView(bannerExpressAd, viewList);
            }
        }
        bindDataToViewPager(viewList);
    }

    /**
     * 向布局中添加广告视图。
     *
     * @param bannerExpressAd 广告对象
     */
    private void addAdView(BannerExpressAd bannerExpressAd, List<View> viewList) {
        View inflate = LayoutInflater.from(BannerGallerySelfActivity.this).inflate(R.layout.page_ad_layout, null);
        BannerAdRootView bannerAdRootView = inflate.findViewById(R.id.ad_layout);
        ImageView adImageView = inflate.findViewById(R.id.ad_banner_img);
        List<String> images = bannerExpressAd.getImages();
        int radius = 0;
        if (bannerExpressAd.getStyle() != null) {
            radius = bannerExpressAd.getStyle().getBorderRadius();
        }
        if (images != null && !images.isEmpty()) {
            GlideUtils.loadImage(BannerGallerySelfActivity.this, images.get(0),adImageView, radius);
        }
        AdFlagCloseView adFlagCloseView = inflate.findViewById(R.id.ad_flag_close_view);
        bannerAdRootView.setAdCloseView(adFlagCloseView);
        // 设置广告负反馈回调监听器
        bannerAdRootView.setDislikeItemClickListener(new DislikeItemClickListener() {
            @Override
            public void onFeedItemClick(int position, @Nullable DislikeInfo dislikeInfo, @Nullable View view) {
                ToastUtils.showShortToast(R.string.app_ad_close_tip);
                ArrayList<View> viewList = new ArrayList<>();
                for (int i = 0; i < MEDIA_ITEM_COUNT; i++) {
                    addDefaultView(viewList);
                }
                bindDataToViewPager(viewList);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onShow() {

            }
        });
        AdActionListener adActionListener = new AdActionListener(TAG);
        // 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
        bannerExpressAd.setAdListener(adActionListener);
        bannerAdRootView.setAd(bannerExpressAd);
        viewList.add(bannerAdRootView);
    }

    /**
     * 向布局中添加默认的图像视图。
     */
    private void addDefaultView(List<View> viewList) {
        ImageView imageView = (ImageView) LayoutInflater.from(BannerGallerySelfActivity.this)
                .inflate(R.layout.view_pager_item_image, null, false);
        Glide.with(BannerGallerySelfActivity.this).load(mImgUrl).into(imageView);
        viewList.add(imageView);
    }

    private void bindDataToViewPager(List<View> viewList) {
        if (CollectionUtils.isNotEmpty(viewList)) {
            showViewPager();
            mViewPager.setAdapter(new BannerPageAdapter(viewList));
            mViewPager.setOffscreenPageLimit(viewList.size());
            mViewPager.setCurrentItem(0);
        }
    }

    /**
     * 显示错误文本并隐藏广告加载布局和 ViewPager。
     *
     * @param errorMsg 错误消息文本
     */
    private void displayOnError(String errorMsg) {
        showErrorText(errorMsg, View.VISIBLE);
        mAdLoadLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
    }

    /**
     * 设置错误信息文本并控制其可见性的方法。
     *
     * @param text 错误信息文本
     * @param visibility 错误信息文本视图的可见性
     */
    private void showErrorText(String text, int visibility) {
        // 检查错误信息文本视图是否存在
        if (mTextErrorInfo != null) {
            // 设置错误信息文本
            mTextErrorInfo.setText(text);
            // 设置错误信息文本视图的可见性
            mTextErrorInfo.setVisibility(visibility);
        }
    }

    /**
     * 隐藏错误文本并将广告加载布局隐藏，同时显示 ViewPager。
     */
    private void showViewPager() {
        showErrorText("", View.GONE);
        mAdLoadLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    /**
     * 页面不可见需要移除广告view
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAd();
    }

    /**
     * 销毁广告对象
     */
    private void releaseAd() {
        if (mBannerExpressAd != null) {
            LogUtil.info(TAG, "releaseAd...");
            mBannerExpressAd.release();
        }
    }


}
