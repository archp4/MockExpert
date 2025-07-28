package com.group7.mockexpert;

import android.os.Bundle;
import android.widget.TextView;

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
import com.group7.mockexpert.models.ReadingTest;
import com.group7.mockexpert.models.ReadingTestResponse;

import java.util.ArrayList;
import java.util.List;

public class ReadingHomeActivity extends AppCompatActivity implements ReadingListener {

    RecyclerView recyclerView;
    TextView textView;
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
        textView=findViewById(R.id.reading_home_loading);
        recyclerView=findViewById(R.id.rv_passage);
        ReadingService service = new ReadingService(this);
        service.connectAndRequestReadingTest(this);
    }


    @Override
    public void onReceive(ReadingTestResponse incomingData) {
        textView.setText("");
        List<Passage> passageList = new ArrayList<Passage>();
        ReadingTest test = incomingData.getReading_test();
        passageList.add(test.getPassage_1());
        passageList.add(test.getPassage_2());
        passageList.add(test.getPassage_3());
        RecyclerView rvPassages = findViewById(R.id.rv_passage);
        rvPassages.setLayoutManager(new LinearLayoutManager(this));
        rvPassages.setAdapter(new PassageAdapter(this, passageList));
    }

    @Override
    public void onError(String errorMessage) {

    }
}