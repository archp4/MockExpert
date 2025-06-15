package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class PartSelectionWritingFragment extends Fragment {

    public PartSelectionWritingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_part_selection_writing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cardTask1 = view.findViewById(R.id.cv_task1);
        CardView cardTask2 = view.findViewById(R.id.cv_task2);

        cardTask1.setOnClickListener(v -> openListTask1());
        cardTask2.setOnClickListener(v -> openListTask2());
    }

    private void openListTask1() {
        Fragment task1Fragment = new WritingTask1Fragment(); // Make sure this fragment is created
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, task1Fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openListTask2() {
        Fragment task2Fragment = new WritingTask2Fragment(); // Make sure this fragment is created
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, task2Fragment)
                .addToBackStack(null)
                .commit();
    }
}
