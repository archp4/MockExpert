package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WritingOpinionEssayFragment extends Fragment {

    private EditText etAnswer;

    public WritingOpinionEssayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_writing_opinion_essay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAnswer = view.findViewById(R.id.et_answerOpinionEssay);
        View submitBtn = view.findViewById(R.id.btn_submitLineChart);

        submitBtn.setOnClickListener(v -> btnSubmit());
    }

    private void btnSubmit() {
        String answer = etAnswer.getText().toString();
        if (answer.isEmpty()) {
            Toast.makeText(requireContext(), "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Answer Submitted.", Toast.LENGTH_SHORT).show();
            // Navigate back to WritingTask2Fragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WritingTask2Fragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
