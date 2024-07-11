package com.hihonor.adsdk.demo.external;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.hihonor.adsdk.base.HnAds;
import com.hihonor.adsdk.demo.external.banner.BannerActivity;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.interstital.InterstitialActivity;
import com.hihonor.adsdk.demo.external.picturetext.PictureTextActivity;
import com.hihonor.adsdk.demo.external.reward.RewardActivity;
import com.hihonor.adsdk.demo.external.splash.SplashActivity;
import com.hihonor.adsdk.tools.ToolsMainActivity;

import static com.hihonor.adsdk.base.BuildConfig.SDK_VERSION;

public class MainActivity extends BaseActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        findViewById(R.id.bt_splash).setOnClickListener(view -> {
            intent.setClass(MainActivity.this, SplashActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_banner).setOnClickListener(view -> {
            intent.setClass(MainActivity.this, BannerActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_pic_text).setOnClickListener(view -> {
            intent.setClass(MainActivity.this,PictureTextActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_reward).setOnClickListener(view -> {
            intent.setClass(MainActivity.this, RewardActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_interstitial).setOnClickListener(view -> {
            intent.setClass(MainActivity.this, InterstitialActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_tools).setOnClickListener(view -> {
            intent.setClass(MainActivity.this, ToolsMainActivity.class);
            startActivity(intent);
        });

        TextView textView = findViewById(R.id.sdk_version);
        textView.setText(getString(R.string.demo_ad_sdk_version) + SDK_VERSION);
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            HnAds.get().getAdManager().requestPermissions(this);
        }
    }
}
