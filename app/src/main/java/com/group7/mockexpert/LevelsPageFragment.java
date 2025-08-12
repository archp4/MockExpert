package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LevelsPageFragment extends Fragment {

    public LevelsPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_levels_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLevel1 = view.findViewById(R.id.btn_level1);
        Button btnLevel2 = view.findViewById(R.id.btn_level2);
        Button btnLevel3 = view.findViewById(R.id.btn_level3);
        Button btnLevel4 = view.findViewById(R.id.btn_level4);

        btnLevel1.setOnClickListener(v -> openPartSelection());
        btnLevel2.setOnClickListener(v -> openPartSelection());
        btnLevel3.setOnClickListener(v -> openPartSelection());
        btnLevel4.setOnClickListener(v -> openPartSelection());
    }

    private void openPartSelection() {
        Fragment partSelectionFragment = new PartSelectionSpeakingFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, partSelectionFragment)
                .addToBackStack(null)
                .commit();
    }
}
