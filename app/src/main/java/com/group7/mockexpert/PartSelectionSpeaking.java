package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.group7.mockexpert.api_helpers.APIConnectionService;

public class PartSelectionSpeaking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_part_selection_speaking);

        CardView cardView = findViewById(R.id.cv_part1);
        cardView.setOnClickListener(v -> {
            //APIConnectionService.TestConnection("http://10.0.0.102:8000/", this);
            Intent intent = new Intent(this, SpeakingIntroductionActivity.class);
            startActivity(intent);
        });

        CardView cardView2 = findViewById(R.id.cv_part2);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(this, CueCard.class);
            startActivity(intent);
        });

        CardView cardView3 = findViewById(R.id.cv_part3);
        cardView3.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpeakingPart3.class);
            startActivity(intent);
        });
    }
}