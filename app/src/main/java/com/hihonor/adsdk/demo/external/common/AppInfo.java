package com.hihonor.adsdk.demo.external.common;

import android.graphics.drawable.Drawable;

/**
 * 功能描述
 *
 * @since 2023-05-08
 */
public class AppInfo {
    private Drawable iconDrawable;
    private String appName;
    private String pkgName;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
