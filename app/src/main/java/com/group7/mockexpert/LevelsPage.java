package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_levels_page);
    }

    public void openLevel1(View view) {
        Intent intent = new Intent(LevelsPage.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }
    public void openLevel2(View view) {
        Intent intent = new Intent(LevelsPage.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }
    public void openLevel3(View view) {
        Intent intent = new Intent(LevelsPage.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }
    public void openLevel4(View view) {
        Intent intent = new Intent(LevelsPage.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }
}