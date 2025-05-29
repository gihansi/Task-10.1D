package com.example.pleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONException;

public class UpgradeActivity extends AppCompatActivity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        paymentsClient = Wallet.getPaymentsClient(this,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build());

        Button purchaseBtn1 = findViewById(R.id.purchaseBtn1);
        Button purchaseBtn2 = findViewById(R.id.purchaseBtn2);
        Button purchaseBtn3 = findViewById(R.id.purchaseBtn3);

        purchaseBtn1.setOnClickListener(v -> payWithGooglePay("5.00")); // Starter
        purchaseBtn2.setOnClickListener(v -> payWithGooglePay("10.00")); // Intermediate
        purchaseBtn3.setOnClickListener(v -> payWithGooglePay("20.00")); // Advanced
    }

    private void payWithGooglePay(String price) {
        try {
            Task<PaymentData> task = paymentsClient.loadPaymentData(
                    com.google.android.gms.wallet.PaymentDataRequest.fromJson(
                            PaymentsUtil.createPaymentDataRequest(price).toString()));
            AutoResolveHelper.resolveTask(task, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        } catch (JSONException e) {
            Toast.makeText(this, "Payment JSON error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    String paymentInfo = paymentData.toJson();
                    Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show();
                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "Payment cancelled.", Toast.LENGTH_SHORT).show();
                    break;

                case AutoResolveHelper.RESULT_ERROR:
                    Toast.makeText(this, "Payment error occurred.", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
