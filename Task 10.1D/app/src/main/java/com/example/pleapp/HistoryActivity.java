package com.example.pleapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout historyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyContainer = findViewById(R.id.historyContainer);
        loadHistory();
    }

    private void loadHistory() {
        SharedPreferences prefs = getSharedPreferences("quiz_history", MODE_PRIVATE);
        String historyStr = prefs.getString("history", "[]");

        try {
            JSONArray historyArray = new JSONArray(historyStr);
            for (int i = 0; i < historyArray.length(); i++) {
                JSONObject historyObj = historyArray.getJSONObject(i);
                long timestamp = historyObj.getLong("timestamp");
                JSONArray attempt = historyObj.getJSONArray("attempt");

                String date = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(new Date(timestamp));

                TextView dateText = new TextView(this);
                dateText.setText("Attempt on: " + date);
                dateText.setTextSize(16f);
                historyContainer.addView(dateText);

                for (int j = 0; j < attempt.length(); j++) {
                    JSONObject q = attempt.getJSONObject(j);
                    String question = q.getString("question");
                    String userAnswer = q.getString("userAnswer");
                    String correct = q.getString("correct");
                    boolean isCorrect = q.getBoolean("isCorrect");

                    TextView qText = new TextView(this);
                    qText.setText((j + 1) + ". " + question + "\nYour answer: " + userAnswer + "\nCorrect answer: " + correct + "\nResult: " + (isCorrect ? "✅ Correct" : "❌ Incorrect"));
                    qText.setPadding(10, 10, 10, 10);
                    historyContainer.addView(qText);
                }

                TextView divider = new TextView(this);
                divider.setText("------------------------------------------------");
                historyContainer.addView(divider);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
