package com.golpobazz.magazine;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import vcm.github.webkit.proview.ProWebResourceError;
import vcm.github.webkit.proview.ProWebResourceRequest;
import vcm.github.webkit.proview.ProWebView;
import vcm.github.webkit.proview.ProWebViewControls;

public class MainActivity extends AppCompatActivity {

    private ProWebView webView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog  = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);
        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
        Glide.with(MainActivity.this)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget);

        webView = findViewById(R.id.webview);
        webView.setActivity(this);
        dialog.show();
        webView.loadUrl("https://golpobazz.com");
        webView.setProClient(new ProWebView.ProClient() {
            @Override
            public boolean shouldOverrideUrlLoading(ProWebView webView, String url) {
                return false;
            }

            @Override
            public void onReceivedError(ProWebView webView, ProWebResourceRequest resourceRequest, ProWebResourceError webResourceError) {
                super.onReceivedError(webView, resourceRequest, webResourceError);

            }

            @Override
            public void onProgressChanged(ProWebView webView, int progress) {
                if(progress == 100){
                    // Page loading finish
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
