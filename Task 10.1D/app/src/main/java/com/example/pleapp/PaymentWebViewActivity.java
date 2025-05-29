package com.example.pleapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentWebViewActivity extends AppCompatActivity {

    WebView paymentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);

        paymentWebView = findViewById(R.id.paymentWebView);

        WebSettings webSettings = paymentWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        paymentWebView.setWebViewClient(new WebViewClient());

        String url = getIntent().getStringExtra("payment_url");
        if (url != null && !url.isEmpty()) {
            Toast.makeText(this, "Loading: " + url, Toast.LENGTH_SHORT).show();
            paymentWebView.loadUrl(url);
        } else {
            Toast.makeText(this, "No payment URL provided.", Toast.LENGTH_LONG).show();
        }

    }
}
