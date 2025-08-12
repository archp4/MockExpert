package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WritingTask2Fragment extends Fragment {

    public WritingTask2Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_writing_task2, container, false);

        // Set click listeners on the CardViews
        view.findViewById(R.id.cv_opinionEssay).setOnClickListener(v -> openOpinionEssays());
        view.findViewById(R.id.cv_problemSolution).setOnClickListener(v -> openProblemSolutionEssays());
        view.findViewById(R.id.cv_advDisadv).setOnClickListener(v -> openAdvantageDisadvantageEssays());

        return view;
    }

    private void openOpinionEssays() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingOpinionEssayFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openProblemSolutionEssays() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingProblemSolutionEssayFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openAdvantageDisadvantageEssays() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingAdvantageDisadvantageEssayFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
