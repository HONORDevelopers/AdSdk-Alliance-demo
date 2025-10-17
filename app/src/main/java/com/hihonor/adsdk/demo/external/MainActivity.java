package com.hihonor.adsdk.demo.external;

import static com.hihonor.adsdk.base.BuildConfig.SDK_VERSION;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.hihonor.adsdk.base.HnAds;
import com.hihonor.adsdk.demo.external.banner.BannerActivity;
import com.hihonor.adsdk.demo.external.common.BaseActivity;
import com.hihonor.adsdk.demo.external.interstital.InterstitialActivity;
import com.hihonor.adsdk.demo.external.picturetext.PictureTextActivity;
import com.hihonor.adsdk.demo.external.reward.RewardActivity;
import com.hihonor.adsdk.demo.external.splash.SplashActivity;
import com.hihonor.adsdk.demo.external.utils.ToastUtils;
import com.hihonor.adsdk.tools.ToolsMainActivity;

import java.util.Objects;

public class MainActivity extends BaseActivity {
    private String visit;
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
            intent.setClass(MainActivity.this, PictureTextActivity.class);
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
        setVisit();
    }

    private void setVisit() {
        EditText editText = findViewById(R.id.et_visitId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                visit = editable.toString();
            }
        });

        findViewById(R.id.save_visit).setOnClickListener(v -> {
            if (Objects.nonNull(visit)) {
                HnAds.get().getAdManager().startSource(visit);
                ToastUtils.showShortToast("保存成功！");
            } else {
                ToastUtils.showShortToast("请输入访问源");
            }
        });
    }


    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 请求权限接口，初始化后才能调用
            HnAds.get().getAdManager().requestPermissions(this);
        }
    }
}
