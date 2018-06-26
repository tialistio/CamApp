package com.example.a1.camapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    private ViewGroup hiddenPanel;
    private TextureView textureView;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        hiddenPanel = (ViewGroup)findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.INVISIBLE);

        textureView = (TextureView)findViewById(R.id.textureView);

        textureView.setSurfaceTextureListener(this);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                textureView.setRotation(90.0f);
                break;
            case Surface.ROTATION_90:
                textureView.setRotation(-180.0f);
                break;
            case Surface.ROTATION_180:
                textureView.setRotation(-90.0f);
                break;
            case Surface.ROTATION_270:
                textureView.setRotation(-180.0f);
                break;
        }

        try {
            camera = Camera.open();
        } catch (Exception e) {

        }
    }

    public void onSlideUpDown(final View view) {
        if (!isPanelShown()) {
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        } else {
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        try {
            camera.stopPreview();
            camera.release();
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
