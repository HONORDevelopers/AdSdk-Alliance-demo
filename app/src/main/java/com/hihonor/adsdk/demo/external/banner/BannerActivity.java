package com.hihonor.adsdk.demo.external.banner;

import android.content.Intent;
import android.os.Bundle;

import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;

public class BannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_banner_ad));
        setContentView(R.layout.activity_banner);
        initView();
    }

    private void initView() {
        findViewById(R.id.bt_default).setOnClickListener(view -> {
            startActivity(new Intent(this, BannerDefaultActivity.class));
        });

        findViewById(R.id.bt_gallery).setOnClickListener(view -> {
            startActivity(new Intent(this, BannerGalleryActivity.class));
        });
    }
}
