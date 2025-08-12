package com.group7.mockexpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WritingTask1Fragment extends Fragment {

    public WritingTask1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_writing_task1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cv_lineChart).setOnClickListener(v -> openLineChart());
        view.findViewById(R.id.cv_barGraph).setOnClickListener(v -> openBarGraph());
        view.findViewById(R.id.cv_pieChart).setOnClickListener(v -> openPieChart());
    }

    public void openLineChart() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingLinechartFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openBarGraph() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingBarGraphFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openPieChart() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new WritingPieChartFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
