package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.group7.mockexpert.api_helpers.QuestionApiService;

public class WritingLinechart extends AppCompatActivity {

    EditText etAnswer;
    TextView tvQuestion;
    ImageView ivChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writing_line_chart);

        etAnswer = findViewById(R.id.et_answerLineChart);
        tvQuestion = findViewById(R.id.tv_questionLineChart);
        ivChart = findViewById(R.id.iv_lineChart);

        QuestionApiService.fetchQuestion(this, 0, new QuestionApiService.QuestionCallback() {
            @Override
            public void onSuccess(String question, String imageUrl) {
                tvQuestion.setText(question);
                Glide.with(WritingLinechart.this).load(imageUrl).into(ivChart);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(WritingLinechart.this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
