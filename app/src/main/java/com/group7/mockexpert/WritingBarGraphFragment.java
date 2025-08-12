package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WritingBarGraphFragment extends Fragment {

    private EditText etAnswer;

    public WritingBarGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writing_bar_graph, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAnswer = view.findViewById(R.id.et_answerBarGraph);

        view.findViewById(R.id.btn_submitBarGraph).setOnClickListener(v -> {
            String answer = etAnswer.getText().toString().trim();
            if (answer.isEmpty()) {
                Toast.makeText(requireContext(), "Please write your answer.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Answer Submitted.", Toast.LENGTH_SHORT).show();

                // Navigate to WritingTask1Fragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new WritingTask1Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
