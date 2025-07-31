package com.group7.mockexpert;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.group7.mockexpert.adapter.PassageAdapter;
import com.group7.mockexpert.models.Passage;
import java.util.List;

public class ReadingPassageFragment extends Fragment {

    private List<Passage> passageList;

    public ReadingPassageFragment(List<Passage> passageList) {
        this.passageList=passageList;
    }
    public static ReadingPassageFragment newInstance(List<Passage> passageList) {
        return new ReadingPassageFragment(passageList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading_passage_fragement, container, false);
        RecyclerView rvPassages = view.findViewById(R.id.rv_passage);
        rvPassages.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassages.setAdapter(new PassageAdapter(view.getContext(), passageList));
        return view;
    }
}