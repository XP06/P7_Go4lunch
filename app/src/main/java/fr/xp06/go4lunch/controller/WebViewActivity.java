package fr.xp06.go4lunch.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import fr.xp06.go4lunch.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static fr.xp06.go4lunch.controller.RestaurantActivity.BUNDLE_EXTRA_URL;

public class WebViewActivity extends AppCompatActivity {

    private String restaurantUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (getIntent().hasExtra(BUNDLE_EXTRA_URL)) {
            restaurantUrl = getIntent().getStringExtra(BUNDLE_EXTRA_URL);
        }
        this.configureWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(restaurantUrl);
    }
}
