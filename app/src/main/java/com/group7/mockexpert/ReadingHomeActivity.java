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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.adapter.PassageAdapter;
import com.group7.mockexpert.api_helpers.ReadingService;
import com.group7.mockexpert.api_helpers.ReadingListener;
import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.Question;
import com.group7.mockexpert.models.ReadingTest;
import com.group7.mockexpert.models.ReadingTestResponse;
import com.group7.mockexpert.viewmodel.ReadingTestViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadingHomeActivity extends AppCompatActivity implements ReadingListener {


    private TextView textView;
    private ReadingPassageFragment passageFragment;
    private QuestionsFragment questionsFragment;
    private ReadingTestViewModel viewModel;
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
        viewModel = new ViewModelProvider(this).get(ReadingTestViewModel.class);
        viewModel.getPassagesLiveData().observe(this, passages -> {
            if (passages != null) {
                showPassages(passages);
            }
        });
        if (viewModel.getPassagesLiveData().getValue() == null) {
            ReadingService service = new ReadingService(this);
            service.connectAndRequestReadingTest(this);
        }

    }


    private void showPassages(List<Passage> passages) {
        textView.setVisibility(View.GONE);
        List<List<Question>> questionList = new ArrayList<List<Question>>();
        for (Passage passage : passages) {
            questionList.add(passage.getQuestions());
        }
        if (!passages.isEmpty()) {
            try {
                passageFragment = new ReadingPassageFragment(passages);
                questionsFragment = new QuestionsFragment(questionList.get(0), questionList.get(1), questionList.get(2));
            } catch (Exception e) {
                Log.e("Create Fragment", Objects.requireNonNull(e.getMessage()));
            }
            ReadingPassageFragment passageFragment = ReadingPassageFragment.newInstance(new ArrayList<>(passages));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.readingFragmentHolder, passageFragment)
                    .commit();

        }
    }

    private void getScore(){
        int score = viewModel.calculateScore();
        Toast.makeText(this, "Your score is " + score, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(List<Passage> passageList) {
        textView.setVisibility(View.GONE);
        viewModel.setPassages(passageList);
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