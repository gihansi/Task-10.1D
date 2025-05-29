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

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin, signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        signupLink = findViewById(R.id.signupLink);
        EditText etUsername = findViewById(R.id.etUsername);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:5001/api/users/login";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", username);
                jsonBody.put("password", password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    },
                    error -> {
                        Toast.makeText(this, "Login failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );

            VolleyActivity.getInstance(this).addToRequestQueue(request);
        });

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();

            if(!username.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            }
        });

        signupLink.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SignupActivity.class))
        );
    }
}