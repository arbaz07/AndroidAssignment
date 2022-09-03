package com.example.androidassignment;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kaopiz.kprogresshud.KProgressHUD;

public class WebView extends AppCompatActivity {
    private android.webkit.WebView webView;
    private KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getSupportActionBar().hide(); //Hide the action bar from the WebView Screen!

        webView = findViewById(R.id.webView);
        webView = new android.webkit.WebView(this);

        webViewLoad();
    }

    /**
     * Load WebView of Cam and Audio Accordingly
     * Function Calling of Load and Dismiss Progress
     */
    private void webViewLoad() {
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        checkWebView();

        setContentView(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                loadProgress();
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                dismissProgressAndCheckPermissions();
            }
        });
    }

    /**
     * This function will check constant and display web-view accordingly
     */
    private void checkWebView() {
        if (SetAndGetData.getInstance().isCamWebView()) {
            webView.loadUrl(Constants.camWebView);
        } else {
            webView.loadUrl(Constants.audioWebView);
        }
    }

    /**
     * Load progress with information text
     */
    private void loadProgress() {
        try {
            progressHUD = KProgressHUD.create(this)
                    .setDimAmount(0.7f)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please Wait!")
                    .show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Dismiss Progress if the value is not null and function calling of permissions check
     */
    private void dismissProgressAndCheckPermissions() {
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
        checkPermissions();
    }

    /**
     * Check and grant permission for Camera if the value of cam web-view is true
     * Check and grant permission for Audio Record and Play if the value of cam web-view is false
     */
    private void checkPermissions() {
        String[] camPermissions = {Manifest.permission.CAMERA};
        String[] audioPermissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO};

        // Permission For Cam
        if (SetAndGetData.getInstance().isCamWebView()) {
            ActivityCompat.requestPermissions(
                    this,
                    camPermissions,
                    1010);
        } else {
            // Permission For Audio Record & Play
            ActivityCompat.requestPermissions(
                    this,
                    audioPermissions,
                    1010);
        }

        // Grant Permission for using the required resources (camera, audio)
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
        });
    }

    /**
     * Handle back-press of Web-view screen and set cam values to false
     * This method also set home item to selected
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (SetAndGetData.getInstance().isCamWebView()) {
            SetAndGetData.getInstance().setCamWebView(false);
        }
        SetAndGetData.getInstance().getBottomNavigationView().setSelectedItemId(R.id.home);
    }
}