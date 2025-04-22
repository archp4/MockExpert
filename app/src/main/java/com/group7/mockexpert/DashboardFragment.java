package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Card: Speaking → opens LevelsPage activity
        CardView cardSpeaking = view.findViewById(R.id.card_speaking);
        cardSpeaking.setOnClickListener(v -> {
            Fragment LevelsPageFragment = new LevelsPageFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, LevelsPageFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Card: Writing → opens PartSelectionWriting activity
        CardView cardWriting = view.findViewById(R.id.card_writing);
        cardWriting.setOnClickListener(v -> {
            Fragment PartSelectionWritingFragment = new PartSelectionWritingFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, PartSelectionWritingFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Card: Reading → you can implement logic here
        CardView cardReading = view.findViewById(R.id.card_reading);
        cardReading.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reading module coming soon", Toast.LENGTH_SHORT).show();
        });

        // Card: Listening → you can implement logic here
        CardView cardListening = view.findViewById(R.id.card_listening);
        cardListening.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Listening module coming soon", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
