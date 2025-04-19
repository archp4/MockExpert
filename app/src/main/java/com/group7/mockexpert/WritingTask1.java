package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.Inet4Address;

public class WritingTask1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writing_task1);
    }

    public void openLineChart(View view) {
        Intent intent = new Intent(this, WritingLinechart.class);
        startActivity(intent);
    }

    public void openBarGraph(View view) {
        Intent intent = new Intent(this, WritingBarGraph.class);
        startActivity(intent);
    }

    public void openPieChart(View view) {
        Intent intent = new Intent(this, WritingPieChart.class);
        startActivity(intent);
    }
}