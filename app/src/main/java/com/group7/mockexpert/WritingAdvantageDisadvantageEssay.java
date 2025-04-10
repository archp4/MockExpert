package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WritingAdvantageDisadvantageEssay extends AppCompatActivity {

    EditText etAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advantage_disadvantage_essay);

        etAnswer = findViewById(R.id.et_AdvantageDisadvantage);
    }

    public void btnSubmit(View view) {
        String answer = etAnswer.getText().toString();
        if (answer.isEmpty()){
            Toast.makeText(this, "Please write your answer.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Answer Submitted.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WritingTask2.class);
            startActivity(intent);
        }
    }
}