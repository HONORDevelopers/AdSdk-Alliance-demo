package com.hihonor.adsdk.demo.external.common;

/**
 * 模拟媒体数据bean，这里简单实现了。
 *
 * @since 2023-05-08
 */
public class MediaDataBean {
    private AppInfo mAppInfo;

    public MediaDataBean() {
    }

    public MediaDataBean(AppInfo appInfo) {
        mAppInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        mAppInfo = appInfo;
    }
}
