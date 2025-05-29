package com.example.pleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    EditText userName, email, cfEmail, etPassword, etConfirmPassword, phoneNumber;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        cfEmail = findViewById(R.id.cfEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        phoneNumber = findViewById(R.id.phoneNumber);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> {
            String username = userName.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String confirmEmail = cfEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String phone = phoneNumber.getText().toString().trim();

            if (username.isEmpty() || emailInput.isEmpty() || confirmEmail.isEmpty() ||
                    password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!emailInput.equals(confirmEmail)) {
                Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:5001/api/users/signup"; // Replace YOUR_IP

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", username);
                jsonBody.put("email", emailInput);
                jsonBody.put("password", password);
                jsonBody.put("phone", phone);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, InterestActivity.class));
                        finish();
                    },
                    error -> {
                        Toast.makeText(this, "Signup failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );

            VolleyActivity.getInstance(this).addToRequestQueue(request);
        });
    }
}
