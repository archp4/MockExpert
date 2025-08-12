package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WritingProblemSolutionEssayFragment extends Fragment {

    private EditText etAnswer;

    public WritingProblemSolutionEssayFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_solution_essay, container, false);

        etAnswer = view.findViewById(R.id.et_answerProblemSolution);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> btnSubmitAction());

        return view;
    }

    private void btnSubmitAction() {
        String answer = etAnswer.getText().toString().trim();

        if (answer.isEmpty()) {
            Toast.makeText(getContext(), "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Answer Submitted.", Toast.LENGTH_SHORT).show();

            // Navigate to WritingTask2Fragment
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container, new WritingTask2Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
