package com.group7.mockexpert;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.group7.mockexpert.api_helpers.ListeningListener;
import com.group7.mockexpert.api_helpers.ListeningService;
import com.group7.mockexpert.models.Question;
import com.group7.mockexpert.models.Section;
import java.util.ArrayList;
import java.util.List;

public class ListeningHome extends AppCompatActivity implements ListeningListener {

    private List<Section> sectionList;
    private Button nextSectionButton;
    private Button previousSectionButton;
    private int currentSectionIndex;
    private int maxSectionCount;
    private List<ListeningSectionFragment> sectionFragments;
    private TextView loadingTextView;
    private Button resultButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listening_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        currentSectionIndex = 0;
        nextSectionButton = findViewById(R.id.next_section);
        previousSectionButton = findViewById(R.id.previous_section);
        resultButton = findViewById(R.id.section_result_button);
        nextSectionButton.setOnClickListener(v -> onNextSectionClicked());
        previousSectionButton.setOnClickListener(v -> onPreviousSectionClicked());
        resultButton.setOnClickListener(v -> onResultClicked());
        ListeningService service = new ListeningService(this);
        service.connectAndRequestListeningTest(this);
        nextSectionButton.setEnabled(false);
        previousSectionButton.setEnabled(false);
        resultButton.setEnabled(false);
        loadingTextView = findViewById(R.id.section_loading_textview);
    }

    private void onNextSectionClicked() {
        if (currentSectionIndex < maxSectionCount - 1) {
            currentSectionIndex++;
            nextSectionButton.setEnabled(currentSectionIndex != maxSectionCount - 1);
            previousSectionButton.setEnabled(currentSectionIndex > 0);
            changeSectionFragment();
        }
    }

    private void changeSectionFragment() {
        if (sectionFragments != null && currentSectionIndex < sectionFragments.size()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.section_fragment_holder, sectionFragments.get(currentSectionIndex)).commit();
        }
    }

    private void onPreviousSectionClicked() {
        if (currentSectionIndex > 0) {
            currentSectionIndex--;
            previousSectionButton.setEnabled(currentSectionIndex != 0);
            nextSectionButton.setEnabled(currentSectionIndex < maxSectionCount - 1);
            changeSectionFragment();
        }
    }

    private void onResultClicked() {
        int score = 0;
        for (Section section: sectionList) {
            for (Question q : section.questions) {
                if(q.getUserAnswer() == null) {
//                    throw new Exception("Question " + q.getNumber() + " has no selected answer");
                    score+=0;
                } else if (q.isCorrect()) {
                    score++;
                }
            }
        }
        Toast.makeText(this,"Score :" + score,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<Section> sectionList) {
        maxSectionCount = sectionList.size();
        sectionFragments = new ArrayList<>();
        loadingTextView.setText("");
        this.sectionList=sectionList;
        int temp = 1;
        for (Section section : sectionList) {
            ListeningSectionFragment fragment = new ListeningSectionFragment(section, String.valueOf(temp));
            sectionFragments.add(fragment);
            temp++;
        }
        changeSectionFragment();
        nextSectionButton.setEnabled(true);
        resultButton.setEnabled(true);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}