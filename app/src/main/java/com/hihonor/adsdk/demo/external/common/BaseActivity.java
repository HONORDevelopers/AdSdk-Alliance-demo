package com.hihonor.adsdk.demo.external.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This base activity for support navigate up within activity hierarchy from the action bar.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }




}
