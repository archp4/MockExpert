package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.group7.mockexpert.api_helpers.QuestionApiService;

public class WritingBarGraph extends AppCompatActivity {

    EditText etAnswer;
    TextView tvQuestion, tvWordCount;
    ImageView ivChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_bar_graph);

        etAnswer = findViewById(R.id.et_answerBarGraph);
        tvQuestion = findViewById(R.id.tv_questionBarGraph);
        ivChart = findViewById(R.id.iv_barGraph);
        tvWordCount = findViewById(R.id.tv_wordCount);

        QuestionApiService.fetchQuestion(this, 1, new QuestionApiService.QuestionCallback() {
            @Override
            public void onSuccess(String question, String imageUrl) {
                tvQuestion.setText(question);
                Glide.with(WritingBarGraph.this).load(imageUrl).into(ivChart);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(WritingBarGraph.this, message, Toast.LENGTH_LONG).show();
            }
        });

        etAnswer.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWordCount.setText("Word Count: " + getWordCount(s.toString()));
            }
            public void afterTextChanged(Editable s) {}
        });
    }

    private int getWordCount(String text) {
        if (text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }

    public void btnSubmit(View view) {
        String answer = etAnswer.getText().toString();
        if (answer.isEmpty()) {
            Toast.makeText(this, "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Answer Submitted.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, WritingTask1.class));
        }
    }
}
