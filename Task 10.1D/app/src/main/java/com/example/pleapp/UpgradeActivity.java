package com.example.pleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UpgradeActivity extends AppCompatActivity {

    Button purchaseBtn1, purchaseBtn2, purchaseBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        purchaseBtn1 = findViewById(R.id.purchaseBtn1);
        purchaseBtn2 = findViewById(R.id.purchaseBtn2);
        purchaseBtn3 = findViewById(R.id.purchaseBtn3);

        purchaseBtn1.setOnClickListener(v -> openPaymentWebView("Starter Plan"));
        purchaseBtn2.setOnClickListener(v -> openPaymentWebView("Intermediate Plan"));
        purchaseBtn3.setOnClickListener(v -> openPaymentWebView("Advanced Plan"));
    }

    private void openPaymentWebView(String planName) {
        String paymentUrl = "";

        switch (planName) {
            case "Starter Plan":
                paymentUrl = "https://yourpaymentprovider.com/checkout?plan=starter";
                break;
            case "Intermediate Plan":
                paymentUrl = "https://yourpaymentprovider.com/checkout?plan=intermediate";
                break;
            case "Advanced Plan":
                paymentUrl = "https://yourpaymentprovider.com/checkout?plan=advanced";
                break;
        }

        Intent intent = new Intent(this, PaymentWebViewActivity.class);
        intent.putExtra("payment_url", paymentUrl);
        startActivity(intent);
    }
}
