package com.example.pleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private TextView tvUsername, tvEmail, tvTotalQuestions, tvCorrectAnswers, tvNotification;
    private ImageView profileImage;
    private ImageButton backBtn;
    private Button shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvTotalQuestions = findViewById(R.id.totalQuestionsTextView);
        tvNotification = findViewById(R.id.tvNotification);
        profileImage = findViewById(R.id.profileImage);
        shareBtn = findViewById(R.id.shareBtn);

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("USERNAME");
            String email = intent.getStringExtra("EMAIL");

            if (username != null) tvUsername.setText(username);
            if (email != null) tvEmail.setText(email);
        }

        fetchQuestionHistory();

        if (backBtn != null) {
            backBtn.setOnClickListener(v -> onBackPressed());
        }

        shareBtn.setOnClickListener(v -> {
            String profileUrl = "https://yourapp.com/profile?user=" + tvUsername.getText();
            String shareText = "Hey, check out my profile here:\n" + profileUrl;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Share profile via"));
        });
    }

    private void fetchQuestionHistory() {
        String url = "http://10.0.2.2:5000/getUserHistory?user=JohnDoe";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        int totalQuestions = response.getInt("total_questions");
                        int correctAnswers = response.getInt("correct_answers");
                        String notification = response.optString("notification", "");

                        tvTotalQuestions.setText(String.valueOf(totalQuestions));
                        tvCorrectAnswers.setText(String.valueOf(correctAnswers));

                        if (!notification.isEmpty()) {
                            tvNotification.setText(notification);
                            tvNotification.setVisibility(View.VISIBLE);
                        } else {
                            tvNotification.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: ", e);
                        Toast.makeText(ProfileActivity.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Error fetching history: " + error.getMessage());
                    Toast.makeText(ProfileActivity.this, "Error fetching history", Toast.LENGTH_LONG).show();
                });

        queue.add(request);
    }
}
