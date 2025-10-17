package com.hihonor.adsdk.demo.external;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.StringRes;

import com.hihonor.adsdk.base.HnAds;
import com.hihonor.adsdk.base.init.ActivateStyle;
import com.hihonor.adsdk.base.init.HnAdConfig;
import com.hihonor.adsdk.base.init.HnRewardListener;
import com.hihonor.adsdk.demo.external.utils.LogUtil;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;

public class DemoApplication extends Application {
    private static final String TAG = "MyApplication";

    private static Context sContext;

    /**
     * 媒体ID
     */
    private static String APPId;

    /**
     * 媒体appKey
     */
    private static String APPKey;

    /**
     * 激励点事件
     */
    private static final String REWARD_ACTION = "reward_action";

    /**
     * 单条广告id
     */
    private static final String AD_ID = "ad_id";

    /**
     * 请求id，请求广告时产生的id
     */
    private static final String REQUEST_ID = "request_id";

    /**
     * 广告位id
     */
    private static final String ADUNIT_ID = "adunit_id";

    /**
     * 下载应用的包名
     */
    private static final String APP_PACKAGE = "app_package";

    /**
     * 下载引导弹窗消失时间
     */
    private static final int DIS_MISS_TIME = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        APPId = getString(R.string.app_id);
        APPKey = getString(R.string.app_key);
        // 强烈建议在应用对应的Application#onCreate()方法中调用，避免出现context为null的异常
        doInit(DemoApplication.this);
    }

    public static String getAdUnitId(@StringRes int id) {
        if (sContext == null) {
            return "";
        }
        return sContext.getString(id);
    }

    /**
     * 获取App全局 Application
     *
     * @return Application context
     */
    public static Context getContext() {
        return sContext;
    }

    private static void doInit(Context context) {
        // 建议在application的onCreate进行初始化全局活动栈生命周期监听，否则会影响sdk的正常功能
        HnAds.get().initActivityLifecycle((Application) sContext);
        HnAds.get().init(context, buildConfig());
        HnAds.get().getAdManager().setAppActivateStrategy(ActivateStyle.CONFIRM_DIALOG, DIS_MISS_TIME);
        HnAds.get().getAdManager().registerNotifyListener(installResultBean -> {
            if (installResultBean != null) {
                LogUtil.info(TAG, "installFinish adUnitId:" + installResultBean.getAdUnitId() + "; adRequestId:"
                        + installResultBean.getAdRequestId() + "; adId:" + installResultBean.getAdId() + "; adImageUrl:"
                        + installResultBean.getImgUrl() + "; adBrand:" + installResultBean.getBrand() + "; adTitle:"
                        + installResultBean.getTitle());
            }
        });
    }

    private static HnAdConfig buildConfig() {
        return new HnAdConfig.Builder()
                .setAppId(APPId) // 设置您的媒体ID，媒体ID是您在荣耀广告平台创建的媒体ID，一般为19位数，请勿使用9位数的应用ID
                .setAppKey(APPKey) // 设置您的appKey，appKey是您在荣耀广告平台注册的媒体id对应的密钥:
                .setSupportMultiProcess(false) // 媒体多进程场景需要设置该参数为true，非多进程场景不需要设置。
                .setRewardListener(new HnRewardListener() {
                    @Override
                    public void onReward(Bundle bundle) {
                        if (null == bundle) {
                            return;
                        }
                        int action = bundle.getInt(REWARD_ACTION);
                        String adId = bundle.getString(AD_ID);
                        String reqId = bundle.getString(REQUEST_ID);
                        String adUnitId = bundle.getString(ADUNIT_ID);
                        String packageName = bundle.getString(APP_PACKAGE);
                        LogUtil.info(TAG, "onReward reqId:" + reqId + "; adUnitId:" + adUnitId
                                + "; adId:" + adId + "; action:" + action);
                        ToastUtils.showShortToast("AD：" + packageName + "get reward action：");
                    }
                })
                // 设置是否开启debug日志，默认关闭
                .setDebug(BuildConfig.DEBUG)
                // .setWxOpenAppId("Your WeChat AppId") // 如果您的推广目标有小程序推广的话，此处需要设置。
                .build();
    }
}
