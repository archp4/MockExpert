package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.mockexpert.api_helpers.QuestionTaskTwoService;

public class WritingAdvantageDisadvantageEssay extends AppCompatActivity {

    EditText etAnswer;
    TextView tvQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advantage_disadvantage_essay);

        etAnswer = findViewById(R.id.et_AdvantageDisadvantage);
        tvQuestion = findViewById(R.id.tv_AdvantageDisadvantage);

        QuestionTaskTwoService.fetchQuestion(this, 2, new QuestionTaskTwoService.QuestionCallback() {
            @Override
            public void onSuccess(String question) {
                tvQuestion.setText(question);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(WritingAdvantageDisadvantageEssay.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btnSubmit(View view) {
        String answer = etAnswer.getText().toString();
        if (answer.isEmpty()) {
            Toast.makeText(this, "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Answer Submitted.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, WritingTask2.class));
        }
    }
}
