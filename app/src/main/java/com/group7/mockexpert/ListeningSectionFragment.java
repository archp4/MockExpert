package com.group7.mockexpert;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group7.mockexpert.adapter.TypeQuestionAdapter;
import com.group7.mockexpert.models.Section;

import java.util.Locale;

public class ListeningSectionFragment extends Fragment {

    Section section;
    private TextToSpeech textToSpeech;
    private final String TAG;
    public ListeningSectionFragment(Section section, String TAG) {
        this.section = section;
        this.TAG = TAG;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listening_section, container, false);
        Button playButton = view.findViewById(R.id.section_play_audio);
        TextView tvSectionNumber = view.findViewById(R.id.section_number_tv);
        RecyclerView recyclerView = view.findViewById(R.id.section_questions_recycler_view);
        TextView tvSectionContext = view.findViewById(R.id.section_context_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new TypeQuestionAdapter(view.getContext(), section.questions));
        tvSectionContext.setText(section.context);
        textToSpeech = new TextToSpeech(view.getContext(), (listener)->{
            if(listener == TextToSpeech.SUCCESS){
                textToSpeech.setLanguage(Locale.CANADA);
            }
        });

        playButton.setOnClickListener((viewBtn)->{
            textToSpeech.speak(section.script, TextToSpeech.QUEUE_FLUSH, null, null);
            Toast.makeText(view.getContext(), "Play button clicked", Toast.LENGTH_SHORT).show();
        });

        tvSectionNumber.setText("Section " + TAG);

        return view;
    }
}