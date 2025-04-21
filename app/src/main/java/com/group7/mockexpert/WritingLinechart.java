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
import com.group7.mockexpert.api_helpers.TaskOneResultApiService;

public class WritingLinechart extends AppCompatActivity {

    EditText etAnswer;
    TextView tvQuestion, tvTitle, tvWordCount;
    ImageView ivChart;

    String currentQuestion = "", currentImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_line_chart);

        etAnswer = findViewById(R.id.et_answerLineChart);
        tvQuestion = findViewById(R.id.tv_questionLineChart);
        tvTitle = findViewById(R.id.tv_writingPractice);
        ivChart = findViewById(R.id.iv_lineChart);
        tvWordCount = findViewById(R.id.tv_wordCount);

        QuestionApiService.fetchQuestion(this, 0, new QuestionApiService.QuestionCallback() {
            @Override
            public void onSuccess(String question, String imageUrl) {
                currentQuestion = question;
                currentImageUrl = imageUrl;
                tvQuestion.setText(question);
                Glide.with(WritingLinechart.this).load(imageUrl).into(ivChart);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(WritingLinechart.this, message, Toast.LENGTH_LONG).show();
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
        String answer = etAnswer.getText().toString().trim();
        int wordCount = getWordCount(answer);

        if (answer.isEmpty()) {
            Toast.makeText(this, "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else if (wordCount < 150) {
            Toast.makeText(this, "Answer must be at least 150 words.", Toast.LENGTH_SHORT).show();
        } else {
            TaskOneResultApiService.submitTaskOne(this, currentQuestion, answer, currentImageUrl, new TaskOneResultApiService.ResultCallback() {
                @Override
                public void onSuccess(int band, String feedback) {
                    tvTitle.setText("Result");
                    tvQuestion.setText("Overall Band: " + band);
                    etAnswer.setText("Feedback: " + feedback);
                    etAnswer.setEnabled(false);
                    ivChart.setVisibility(View.GONE);
                    etAnswer.setMinHeight(0);
                    etAnswer.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvWordCount.setVisibility(View.GONE);
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(WritingLinechart.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
