package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WritingTask2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writing_task2);
    }

    public void openOpinionEssays(View view) {
        Intent intent = new Intent(this, WritingOpinionEssay.class);
        startActivity(intent);
    }

    public void openProblemSolutionEssays(View view) {
        Intent intent = new Intent(this, WritingProblemSolutionEssay.class);
        startActivity(intent);
    }

    public void openAdvantageDisadvantageEssays(View view) {
        Intent intent = new Intent(this, WritingAdvantageDisadvantageEssay.class);
        startActivity(intent);
    }
}