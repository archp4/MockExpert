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

public class WritingAdvantageDisadvantageEssayFragment extends Fragment {

    private EditText etAnswer;

    public WritingAdvantageDisadvantageEssayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_writing_advantage_disadvantage_essay, container, false); // Make sure XML file is renamed to match
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etAnswer = view.findViewById(R.id.et_AdvantageDisadvantage);

        view.findViewById(R.id.btn_submit).setOnClickListener(v -> btnSubmit());
    }

    private void btnSubmit() {
        String answer = etAnswer.getText().toString();
        if (answer.isEmpty()) {
            Toast.makeText(requireContext(), "Please write your answer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Answer Submitted.", Toast.LENGTH_SHORT).show();

            // Replace fragment instead of launching an intent
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new WritingTask2Fragment()); // Ensure this ID matches your layout
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
