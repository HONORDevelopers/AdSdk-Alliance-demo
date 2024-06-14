package com.hihonor.adsdk.demo.external.interstital;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hihonor.adsdk.base.AdSlot;
import com.hihonor.adsdk.base.api.interstitial.InterstitialAdLoadListener;
import com.hihonor.adsdk.base.api.interstitial.InterstitialExpressAd;
import com.hihonor.adsdk.base.callback.AdListener;
import com.hihonor.adsdk.demo.external.DemoApplication;
import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.utils.DensityUtil;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ScreenUtils;
import com.hihonor.adsdk.interstitial.InterstitialAdLoad;

public class InterstitialActivity extends BaseActivity {

    private final static String TAG = "InterstitialActivity";

    private static final int RADIO_MARGIN_DP = 4;

    private static final int MARGIN_DP = 16;

    private static final int RADIO_AMOUNT = 8;

    private static final int HALF = 2;

    private static final int ROW_MARGIN_NUMBER = 3;

    /**
     * 广告位ID
     */
    private String mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_1280_720_portrait_unit_id);

    private TextView mTvMsg;

    /**
     * 广告对象
     */
    private InterstitialExpressAd mInterstitialExpressAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_interstital_ad));
        setContentView(R.layout.activity_interstital);
        initView();
    }

    @SuppressLint("NonConstantResourceId")
    private void initView() {
        Button btnIns = findViewById(R.id.btn_ins);
        RadioGroup radioGroup =  findViewById(R.id.interstitial_size);
        RadioButton picLand1280 = findViewById(R.id.btn_int_pic_1280_720_land);
        RadioButton videoLand1280 = findViewById(R.id.btn_int_video_1280_720_land);
        layoutRadioButtons(btnIns, radioGroup, picLand1280, videoLand1280);
        mTvMsg = findViewById(R.id.tv_msg);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId) {
                case R.id.btn_int_pic_1280_720_land:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_1280_720_land_unit_id);
                    break;
                case R.id.btn_int_pic_1280_720_port:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_1280_720_portrait_unit_id);
                    break;
                case R.id.btn_int_pic_720_1280_land:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_720_1280_land_unit_id);
                    break;
                case R.id.btn_int_pic_720_1280_port:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_720_1280_portrait_unit_id);
                    break;
                case R.id.btn_int_video_1280_720_land:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_1280_720_land_video_unit_id);
                    break;
                case R.id.btn_int_video_1280_720_port:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_1280_720_portrait_video_unit_id);
                    break;
                case R.id.btn_int_video_720_1280_land:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_720_1280_land_video_unit_id);
                    break;
                case R.id.btn_int_video_720_1280_port:
                    mSlotId = DemoApplication.getAdUnitId(R.string.interstitial_720_1280_portrait_video_unit_id);
                    break;
                default:
                    LogUtil.error(TAG, getString(R.string.slot_empty_msg));
            }
        });
        btnIns.setOnClickListener(view -> obtainAd());
    }

    private void layoutRadioButtons(Button btnIns, RadioGroup radioGroup, RadioButton picLand1280, RadioButton videoLand1280) {
        picLand1280.post(() -> {
            if (0 == DensityUtil.getScreenOrientation(this)) {
                // 分为两列，一列四个radio
                int marginWidthPort = DensityUtil.getScreenWidth(this) / HALF;
                int marginTop = picLand1280.getMeasuredHeight() * (RADIO_AMOUNT / HALF) + DensityUtil.dip2px(this, RADIO_MARGIN_DP) * ROW_MARGIN_NUMBER;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) videoLand1280.getLayoutParams();
                // 第五个radio的left边距设置为屏幕宽度的一半，top边距为负的前四个radio的总高度
                params.setMargins(marginWidthPort, -marginTop
                        , 0, DensityUtil.dip2px(this, RADIO_MARGIN_DP));
                videoLand1280.setLayoutParams(params);

                // 每个radio宽度设置为屏幕宽度-两边边距
                int expectedWidth = marginWidthPort - DensityUtil.dip2px(this, MARGIN_DP) - DensityUtil.dip2px(this, MARGIN_DP / HALF);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    LinearLayout.LayoutParams radioBtnParams = (LinearLayout.LayoutParams) radioButton.getLayoutParams();
                    radioBtnParams.width = expectedWidth;
                    radioButton.setLayoutParams(radioBtnParams);
                }
                // 设置加载按钮marginTop,使其在广告尺寸组下方
                LinearLayout.LayoutParams loadBtnParams = (LinearLayout.LayoutParams) btnIns.getLayoutParams();
                loadBtnParams.topMargin = -(marginTop - picLand1280.getMeasuredHeight()-DensityUtil.dip2px(this, RADIO_MARGIN_DP));
                btnIns.setLayoutParams(loadBtnParams);
            } else {
                // 分为两列，一列四个radio
                int marginWidthLand = DensityUtil.getScreenWidth(this) / HALF;
                int marginTop = picLand1280.getMeasuredHeight() * (RADIO_AMOUNT / HALF) + DensityUtil.dip2px(this, RADIO_MARGIN_DP) * ROW_MARGIN_NUMBER;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) videoLand1280.getLayoutParams();
                // 第五个radio的left边距设置为屏幕宽度的一半，top边距为负的前四个radio的总高度
                params.setMargins(marginWidthLand, -marginTop
                        , 0, DensityUtil.dip2px(getApplication(), RADIO_MARGIN_DP));
                videoLand1280.setLayoutParams(params);

                // 每个radio宽度设置为（屏幕长度-两边边距-状态栏）/2
                int expectedWidth = (DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(this, MARGIN_DP) * ROW_MARGIN_NUMBER - ScreenUtils.getStatusBarHeight(this)) / HALF;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    LinearLayout.LayoutParams radioBtnParams = (LinearLayout.LayoutParams) radioButton.getLayoutParams();
                    radioBtnParams.width = expectedWidth;
                    radioButton.setLayoutParams(radioBtnParams);
                }
                // 设置加载按钮marginTop,使其在广告尺寸组下方
                LinearLayout.LayoutParams loadBtnParams = (LinearLayout.LayoutParams) btnIns.getLayoutParams();
                loadBtnParams.topMargin = -(marginTop - picLand1280.getMeasuredHeight()-DensityUtil.dip2px(this, MARGIN_DP));
                btnIns.setLayoutParams(loadBtnParams);
            }
        });
    }

    /**
     * 获取广告
     */
    private void obtainAd() {
        if (TextUtils.isEmpty(mSlotId)) {
            Toast.makeText(InterstitialActivity.this,
                    R.string.slot_empty_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        // step1：创建广告请求参数对象（AdSlot）。
        AdSlot adSlot = new AdSlot.Builder()
                .setSlotId(mSlotId) // 必填，设置广告位ID。
                .build();
        // step4：构建广告加载器，传入已创建好的广告请求参数对象与广告加载状态监听器。
        InterstitialAdLoad load = new InterstitialAdLoad.Builder()
            .setInterstitialAdLoadListener(mAdLoadListener) // 必填，注册广告加载状态监听器。
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
    private final InterstitialAdLoadListener mAdLoadListener = new InterstitialAdLoadListener() {

        /**
         * 广告加载成功回调。
         *
         * @param interstitialExpressAd 插屏广告模板接口基类
         */
        @Override
        public void onAdLoaded(InterstitialExpressAd interstitialExpressAd) {
            LogUtil.info(TAG, "onAdLoaded, ad load success");
            mInterstitialExpressAd = interstitialExpressAd;
            Toast.makeText(InterstitialActivity.this,
                    R.string.load_success, Toast.LENGTH_SHORT).show();
            // 注册广告事件监听器，您可根据需求实现接口并按需重写您需要接收通知的方法。
            mInterstitialExpressAd.setAdListener(new AdListener(){

                /**
                 * 广告曝光时回调
                 */
                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    LogUtil.info(TAG, "onAdImpression...");
                    Toast.makeText(InterstitialActivity.this, getString(R.string.ad_impression_success), Toast.LENGTH_SHORT).show();
                }

                /**
                 * 广告曝光失败时回调
                 *
                 * @param msg 曝光失败信息
                 */
                @Override
                public void onAdImpressionFailed(int errCode, String msg) {
                    super.onAdImpressionFailed(errCode, msg);
                    LogUtil.info(TAG, "onAdImpressionFailed, msg: " + msg);
                    Toast.makeText(InterstitialActivity.this, getString(R.string.ad_impression_failed), Toast.LENGTH_SHORT).show();
                }

                /**
                 * 广告被点击时回调
                 */
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    LogUtil.info(TAG, "onAdClicked...");
                    Toast.makeText(InterstitialActivity.this, getString(R.string.ad_clicked), Toast.LENGTH_SHORT).show();
                }

                /**
                 * 广告关闭时回调
                 */
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    LogUtil.info(TAG, "onAdClosed...");
                    Toast.makeText(InterstitialActivity.this, getString(R.string.app_ad_close_tip), Toast.LENGTH_SHORT).show();
                    releaseAd();
                }

                /**
                 * 广告成功跳转小程序时回调
                 */
                @Override
                public void onMiniAppStarted() {
                    super.onMiniAppStarted();
                    LogUtil.info(TAG, "onMiniAppStarted...");
                    Toast.makeText(InterstitialActivity.this, getString(R.string.miniapp_start), Toast.LENGTH_SHORT).show();
                }
            });
            // step3：在请求成功回调里，使用返回的广告对象作渲染处理。
            mInterstitialExpressAd.show(InterstitialActivity.this);
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
            mTvMsg.setVisibility(View.VISIBLE);
            mTvMsg.setText("err : " + code + ", msg is :" + errorMsg);
        }
    };

    /**
     * 页面不可见时需要销毁广告
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.info(TAG, "onDestroy");
        releaseAd();
    }

    /**
     * 销毁广告
     */
    private void releaseAd() {
        if (mInterstitialExpressAd != null) {
            LogUtil.info(TAG, "releaseAd...");
            mInterstitialExpressAd.release();
        }
    }
}
