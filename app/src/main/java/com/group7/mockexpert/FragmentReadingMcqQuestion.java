package com.group7.mockexpert;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.group7.mockexpert.models.ReadingPassageQuestion;

import java.util.List;

public class FragmentReadingMcqQuestion extends Fragment {

    private static final String ARG_QUESTION = "question";
    private ReadingPassageQuestion currentQuestion;
    private TextView tvQuestionNumber;
    private TextView tvQuestionText;
    private RadioGroup rgOptions;
    private TextView tvFeedback;



    public FragmentReadingMcqQuestion() {
        // Required empty public constructor
    }

    public static FragmentReadingMcqQuestion newInstance(ReadingPassageQuestion question) {
        FragmentReadingMcqQuestion fragment = new FragmentReadingMcqQuestion();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentQuestion = (ReadingPassageQuestion) getArguments().getSerializable(ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading_mcq_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvQuestionNumber = view.findViewById(R.id.tv_question_number);
        tvQuestionText = view.findViewById(R.id.tv_question_text);
        rgOptions = view.findViewById(R.id.rg_options);
        tvFeedback = view.findViewById(R.id.tv_feedback);

        if (currentQuestion != null) {
            displayQuestion(currentQuestion);
        }

    }

    public void displayQuestion(ReadingPassageQuestion question) {
        this.currentQuestion = question;

        if (tvQuestionNumber != null) {
            tvQuestionNumber.setText("Question " + question.getNumber());
        }

        if (tvQuestionText != null) {
            tvQuestionText.setText(question.getQuestion());
        }

        if (rgOptions != null) {
            rgOptions.removeAllViews();

            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                radioButton.setId(View.generateViewId());
                rgOptions.addView(radioButton);
            }
        }

        if (tvFeedback != null) {
            tvFeedback.setVisibility(View.GONE);
        }

        if (rgOptions != null) {
            rgOptions.setEnabled(true);
        }
    }

    private void checkAnswer() {
        if (currentQuestion == null) {
            showFeedback("Error: No question loaded.", Color.RED);
            return;
        }

        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadio = rgOptions.findViewById(selectedId);
        String userAnswer = selectedRadio.getText().toString();
        String correctAnswer = currentQuestion.getAnswer();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            showFeedback("Correct!", Color.parseColor("#4CAF50"));
        } else {
            showFeedback("Incorrect. The correct answer was: " + correctAnswer, Color.RED);
        }


    }

    private void showFeedback(String message, int color) {
        if (tvFeedback != null) {
            tvFeedback.setText(message);
            tvFeedback.setTextColor(color);
            tvFeedback.setVisibility(View.VISIBLE);
        }
    }
}