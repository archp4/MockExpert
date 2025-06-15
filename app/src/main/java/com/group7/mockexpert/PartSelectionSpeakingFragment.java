package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class PartSelectionSpeakingFragment extends Fragment {

    public PartSelectionSpeakingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_part_selection_speaking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cardPart1 = view.findViewById(R.id.cv_part1);
        CardView cardPart2 = view.findViewById(R.id.cv_part2);
        CardView cardPart3 = view.findViewById(R.id.cv_part3);

        cardPart1.setOnClickListener(v -> {
            Fragment fragment = new SpeakingIntroductionFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        cardPart2.setOnClickListener(v -> {
            Fragment fragment = new CueCardFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        cardPart3.setOnClickListener(v -> {
            Fragment fragment = new SpeakingPart3Fragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
