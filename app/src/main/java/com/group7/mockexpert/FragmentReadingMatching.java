package com.group7.mockexpert;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

public class FragmentReadingMatching extends Fragment {

    private LinearLayout llSentencesContainer;

    public FragmentReadingMatching() {
        // Required empty public constructor
    }

    public static FragmentReadingMatching newInstance() {
        return new FragmentReadingMatching();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading_matching, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llSentencesContainer = view.findViewById(R.id.ll_sentences_container);

        // Sample data
        List<String> sentences = Arrays.asList(
                "The Earth's temperature is rising.",
                "Greenhouse gases trap heat in the atmosphere.",
                "Carbon emissions have increased rapidly.",
                "Deforestation reduces natural CO2 absorption.",
                "Climate change impacts global weather patterns."
        );

        addMatchingQuestions(sentences);
    }

    private void addMatchingQuestions(List<String> sentences) {
        for (String sentenceText : sentences) {
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            row.setPadding(0, dpToPx(8), 0, dpToPx(8));

            // Bullet point
            TextView bullet = new TextView(requireContext());
            bullet.setText("•");
            bullet.setTextSize(18);
            bullet.setPadding(0, 0, dpToPx(6), 0);

            // Sentence TextView
            TextView sentenceView = new TextView(requireContext());
            sentenceView.setText(sentenceText);
            sentenceView.setTextSize(16);
            sentenceView.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // EditText for paragraph number
            EditText paraInput = new EditText(requireContext());
            paraInput.setHint("Type here");
            paraInput.setLayoutParams(new LinearLayout.LayoutParams(
                    dpToPx(60), ViewGroup.LayoutParams.WRAP_CONTENT));

            // Add all views to row
            row.addView(bullet);
            row.addView(sentenceView);
            row.addView(paraInput);

            // Add row to container
            llSentencesContainer.addView(row);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * requireContext().getResources().getDisplayMetrics().density);
    }
}
