package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PartSelectionWriting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_part_selection__writing);

    }

    public void openListTask1(View view) {
        Intent intent = new Intent(this, WritingTask1.class);
        startActivity(intent);
    }

    public void openListTask2(View view) {
        Intent intent = new Intent(this, WritingTask2.class);
        startActivity(intent);
    }
}