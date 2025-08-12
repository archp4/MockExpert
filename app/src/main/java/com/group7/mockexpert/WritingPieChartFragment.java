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
import androidx.fragment.app.FragmentTransaction;

public class WritingPieChartFragment extends Fragment {

    private EditText etAnswer;

    public WritingPieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writing_pie_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAnswer = view.findViewById(R.id.et_answerPieChart);

        view.findViewById(R.id.btn_submitPieChart).setOnClickListener(v -> btnSubmit());
    }

    private void btnSubmit() {
        String answer = etAnswer.getText().toString().trim();

        if (answer.isEmpty()) {
            Toast.makeText(getContext(), "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Answer Submitted.", Toast.LENGTH_SHORT).show();

            // Navigate back to WritingTask1Fragment
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container, new WritingTask1Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
