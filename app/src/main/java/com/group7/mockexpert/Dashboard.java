package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
    }

    public void openSpeakingModule(View view) {
        Intent intent = new Intent(Dashboard.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }

    public void openListeningModule(View view) {

    }

    public void openReadingModule(View view) {

    }

    public void openWritingModule(View view) {
        Intent intent = new Intent(Dashboard.this, PartSelectionWriting.class);
        startActivity(intent);
    }
}