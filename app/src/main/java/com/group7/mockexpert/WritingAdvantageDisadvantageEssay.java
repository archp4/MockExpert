package com.group7.mockexpert;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.mockexpert.api_helpers.QuestionTaskTwoService;
import com.group7.mockexpert.api_helpers.TaskTwoResultApiService;

public class WritingAdvantageDisadvantageEssay extends AppCompatActivity {

    EditText etAnswer;
    TextView tvQuestion, tvTitle, tvWordCount;
    String currentQuestion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advantage_disadvantage_essay);

        etAnswer = findViewById(R.id.et_AdvantageDisadvantage);
        tvQuestion = findViewById(R.id.tv_AdvantageDisadvantage);
        tvTitle = findViewById(R.id.tv_writingPractice);
        tvWordCount = findViewById(R.id.tv_wordCount);

        QuestionTaskTwoService.fetchQuestion(this, 2, new QuestionTaskTwoService.QuestionCallback() {
            @Override
            public void onSuccess(String question) {
                currentQuestion = question;
                tvQuestion.setText(question);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(WritingAdvantageDisadvantageEssay.this, message, Toast.LENGTH_LONG).show();
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
        } else if (wordCount < 250) {
            Toast.makeText(this, "Answer must be at least 250 words.", Toast.LENGTH_SHORT).show();
        } else {
            TaskTwoResultApiService.submitTaskTwo(this, currentQuestion, answer, new TaskTwoResultApiService.ResultCallback() {
                @Override
                public void onSuccess(int band, String feedback) {
                    tvTitle.setText("Result");
                    tvQuestion.setText("Overall Band: " + band);
                    etAnswer.setText(feedback);
                    etAnswer.setEnabled(false);
                    etAnswer.setMinHeight(0);
                    etAnswer.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvWordCount.setVisibility(View.GONE);
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(WritingAdvantageDisadvantageEssay.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
