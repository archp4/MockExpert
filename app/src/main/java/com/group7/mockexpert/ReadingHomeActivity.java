package com.group7.mockexpert;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.adapter.PassageAdapter;
import com.group7.mockexpert.api_helpers.ReadingService;
import com.group7.mockexpert.api_helpers.ReadingListener;
import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.Question;
import com.group7.mockexpert.models.ReadingTest;
import com.group7.mockexpert.models.ReadingTestResponse;

import java.util.ArrayList;
import java.util.List;

public class ReadingHomeActivity extends AppCompatActivity implements ReadingListener {


    TextView textView;
    ReadingPassageFragment passageFragment;
    QuestionsFragment questionsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reading_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView=findViewById(R.id.textviewLoading);
        ReadingService service = new ReadingService(this);
        service.connectAndRequestReadingTest(this);

    }

    @Override
    public void onReceive(List<Passage> passageList) {
        textView.setVisibility(View.GONE);
        List<List<Question>> questionList = new ArrayList<List<Question>>();
        for (Passage passage : passageList) {
            questionList.add(passage.getQuestions());
        }
        passageFragment = new ReadingPassageFragment(passageList);
        try {
            questionsFragment = new QuestionsFragment(questionList.get(0), questionList.get(1), questionList.get(2));
        } catch (Exception e) {
            Log.e("Create Fragment", e.getMessage());
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.readingFragmentHolder, passageFragment).commit();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void viewQuestion(View view) {
        if (questionsFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.readingFragmentHolder, questionsFragment).commit();
        }
    }

    public void viewPassage(View view) {
        if (passageFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.readingFragmentHolder, passageFragment).commit();
        }
    }
}