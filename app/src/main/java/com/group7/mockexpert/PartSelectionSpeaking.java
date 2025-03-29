package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PartSelectionSpeaking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_part_selection_speaking);

        CardView cardView = findViewById(R.id.cv_part1);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpeakingIntroductionActivity.class);
            startActivity(intent);
        });

        CardView cardView2 = findViewById(R.id.cv_part2);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(this, CueCards.class);
            startActivity(intent);
        });
    }
//    public void openSpeakingIntroduction(View view) {
//        Intent intent = new Intent(this, SpeakingIntroductionActivity.class);
//        startActivity(intent);
//    }
}