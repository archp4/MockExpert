package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CueCards extends AppCompatActivity {
    private ListView listView;
    private List<CueCardModel> cueCards;
    private List<String> cueCardTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cue_cards);

        listView = findViewById(R.id.listView_cueCards);
        cueCards = new ArrayList<>();
        cueCardTitles = new ArrayList<>();

        //first cue card bullet points
        List<String> bulletPoints1 = new ArrayList<>();
        bulletPoints1.add("When you visited the place?");
        bulletPoints1.add("Mention why you liked the place.");
        bulletPoints1.add("Explain what made the place memorable.");

        //second cue card bullet points
        List<String> bulletPoints2 = new ArrayList<>();
        bulletPoints2.add("What is your hobby?");
        bulletPoints2.add("Why do you enjoy this hobby?");
        bulletPoints2.add("How did you get started with it?");

        //third cue card bullet points
        List<String> bulletPoints3 = new ArrayList<>();
        bulletPoints3.add("How you found that book?");
        bulletPoints3.add("What is the main theme of the book?");
        bulletPoints3.add("Who is your favorite character and why?");

        cueCards.add(new CueCardModel("Describe a place you visited.", bulletPoints1));
        cueCards.add(new CueCardModel("Talk about your favorite hobby.", bulletPoints2));
        cueCards.add(new CueCardModel("Discuss a book you recently read.", bulletPoints3));

        for (CueCardModel card : cueCards) {
            cueCardTitles.add(card.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cueCardTitles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            CueCardModel selectedCueCard = cueCards.get(position);
            Intent intent = new Intent(this, CueCardDetail.class);
            intent.putExtra("cueCardTitle", selectedCueCard.getTitle());
            intent.putStringArrayListExtra("cueCardBulletPoints", new ArrayList<>(selectedCueCard.getBulletPoints()));
            startActivity(intent);
        });
    }
}