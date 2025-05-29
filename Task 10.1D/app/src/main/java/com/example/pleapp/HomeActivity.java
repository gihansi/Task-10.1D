package com.example.pleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button upgradeButton;
    LinearLayout taskCard;
    ImageButton goToTaskBtn, profileIcon;
    LinearLayout questionsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        taskCard = findViewById(R.id.taskCard);
        goToTaskBtn = findViewById(R.id.goToTaskBtn);
        upgradeButton = findViewById(R.id.upgradeButton);
        profileIcon = findViewById(R.id.profileIcon);
        questionsContainer = findViewById(R.id.questionsContainer);

        // Declare once
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("EMAIL");
        TextView tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText("Welcome, " + username);

        taskCard.setOnClickListener(v -> {
            Toast.makeText(this, "Opening Task...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, QuestionActivity.class));
        });

        goToTaskBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Launching task...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, QuestionActivity.class));
        });

        upgradeButton.setOnClickListener(v -> {
            Toast.makeText(this, "Opening Upgrade Page...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, UpgradeActivity.class));
        });

        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USERNAME", username);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        });
        Button historyButton = new Button(this);
        historyButton.setText("View History");
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
        questionsContainer.addView(historyButton);
    }
}