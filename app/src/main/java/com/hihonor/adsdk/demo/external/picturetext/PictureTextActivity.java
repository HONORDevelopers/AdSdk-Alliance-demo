package com.hihonor.adsdk.demo.external.picturetext;

import android.content.Intent;
import android.os.Bundle;

import com.hihonor.adsdk.demo.external.R;
import com.hihonor.adsdk.demo.external.common.BaseActivity;

public class PictureTextActivity extends BaseActivity {
    public static final String TEMPLATE_TYPE = "templateType";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_picture_text_ad));

        setContentView(R.layout.activity_picture_text);

        findViewById(R.id.picture_text_template_render_big).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_big);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_template_render_small).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_small);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_template_render_three).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_three);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_template_render_app).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_app);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_template_render_landscape).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_landscape);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_template_render_portrait).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextTemplateRenderActivity.class);
            intent.putExtra(TEMPLATE_TYPE, R.id.picture_text_template_render_portrait);
            startActivity(intent);
        });
        findViewById(R.id.picture_text_self_render).setOnClickListener(view -> {
            Intent intent = new Intent(PictureTextActivity.this, PictureTextSelfRenderActivity.class);
            startActivity(intent);
        });
    }
}
