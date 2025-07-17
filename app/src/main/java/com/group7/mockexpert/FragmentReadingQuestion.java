package com.group7.mockexpert; // Keep your package name as is

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;

import com.group7.mockexpert.models.ReadingPassageQuestion;

/**
 * A simple {@link Fragment} subclass for displaying an IELTS Reading question
 * and allowing the user to input and check an answer.
 */
public class FragmentReadingQuestion extends Fragment {

    private static final String ARG_QUESTION = "question";

    private ReadingPassageQuestion currentQuestion;

    private TextView tvQuestionNumber;
    private TextView tvQuestionText;
    private EditText etUserAnswer;
    private Button btnSubmitAnswer;
    private TextView tvFeedback;

    public FragmentReadingQuestion() {
        // Required empty public constructor
    }

    public static FragmentReadingQuestion newInstance(ReadingPassageQuestion question) {
        FragmentReadingQuestion fragment = new FragmentReadingQuestion();
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
        View view = inflater.inflate(R.layout.fragment_reading_question, container, false);

        tvQuestionNumber = view.findViewById(R.id.tv_question_number);
        tvQuestionText = view.findViewById(R.id.tv_question_text);
        etUserAnswer = view.findViewById(R.id.et_user_answer);
        tvFeedback = view.findViewById(R.id.tv_feedback);

        if (currentQuestion != null) {
            displayQuestion(currentQuestion);
        }
        return view;
    }

    public void displayQuestion(ReadingPassageQuestion question) {
        this.currentQuestion = question; // Update the current question
        if (tvQuestionNumber != null) {
            tvQuestionNumber.setText("Question " + question.getNumber());
        }
        if (tvQuestionText != null) {
            tvQuestionText.setText(question.getQuestion());
        }
        if (etUserAnswer != null) {
            etUserAnswer.setText(""); // Clear any previous user input
            etUserAnswer.setEnabled(true); // Ensure input is enabled for new question
        }
        if (btnSubmitAnswer != null) {
            btnSubmitAnswer.setEnabled(true); // Ensure submit button is enabled
        }
        if (tvFeedback != null) {
            tvFeedback.setVisibility(View.GONE); // Hide feedback when a new question is displayed
        }
    }

    private void checkAnswer() {

        if (currentQuestion == null) {
            if (tvFeedback != null) {
                tvFeedback.setText("Error: No question loaded.");
                tvFeedback.setTextColor(Color.RED);
                tvFeedback.setVisibility(View.VISIBLE);
            }
            return;
        }

        String userAnswer = etUserAnswer.getText().toString().trim();
        String correctAnswer = currentQuestion.getAnswer().trim();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            if (tvFeedback != null) {
                tvFeedback.setText("Correct!");
                tvFeedback.setTextColor(Color.parseColor("#4CAF50")); // Green color for correct
            }
        } else {
            if (tvFeedback != null) {
                tvFeedback.setText("Incorrect. The correct answer was: " + correctAnswer);
                tvFeedback.setTextColor(Color.RED); // Red color for incorrect
            }
        }

        if (tvFeedback != null) {
            tvFeedback.setVisibility(View.VISIBLE);
        }

        if (etUserAnswer != null) {
            etUserAnswer.setEnabled(false);
        }
        if (btnSubmitAnswer != null) {
            btnSubmitAnswer.setEnabled(false);
        }
    }
}
