package com.group7.mockexpert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group7.mockexpert.adapter.PassageQuestionAdapter;
import com.group7.mockexpert.models.Question;
import com.group7.mockexpert.viewmodel.ReadingTestViewModel;

import java.util.List;
import java.util.Map;


public class QuestionsFragment extends Fragment {

    private ReadingTestViewModel viewModel;
    List<Question> passageOneQuestions, passageTwoQuestions, passageThreeQuestions;
    Map<String, String> passageOneQuestionsTypeInstruction, passageTwoQuestionsTypeInstruction, passageThreeQuestionsTypeInstruction;

    public QuestionsFragment(List<Question> passageOneQuestions, List<Question> passageTwoQuestions, List<Question> passageThreeQuestions, Map<String, String> passageOneQuestionsTypeInstruction,Map<String, String> passageTwoQuestionsTypeInstruction,Map<String, String> passageThreeQuestionsTypeInstruction) {
        this.passageOneQuestions = passageOneQuestions;
        this.passageTwoQuestions = passageTwoQuestions;
        this.passageThreeQuestions = passageThreeQuestions;
        this.passageOneQuestionsTypeInstruction = passageOneQuestionsTypeInstruction;
        this.passageTwoQuestionsTypeInstruction = passageTwoQuestionsTypeInstruction;
        this.passageThreeQuestionsTypeInstruction = passageThreeQuestionsTypeInstruction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReadingTestViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_fragement, container, false);

        RecyclerView rvPassageQuestionOne = view.findViewById(R.id.rv_passage_question_list_one);
        RecyclerView rvPassageQuestionTwo = view.findViewById(R.id.rv_passage_question_list_two);
        RecyclerView rvPassageQuestionThree = view.findViewById(R.id.rv_passage_question_list_three);

        rvPassageQuestionOne.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionOne.setAdapter(new PassageQuestionAdapter(view.getContext(), passageOneQuestions, passageOneQuestionsTypeInstruction));
        rvPassageQuestionOne.setNestedScrollingEnabled(false);

        rvPassageQuestionTwo.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionTwo.setAdapter(new PassageQuestionAdapter(view.getContext(), passageTwoQuestions, passageTwoQuestionsTypeInstruction));
        rvPassageQuestionTwo.setNestedScrollingEnabled(false);

        rvPassageQuestionThree.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionThree.setAdapter(new PassageQuestionAdapter(view.getContext(), passageThreeQuestions,passageOneQuestionsTypeInstruction));
        rvPassageQuestionThree.setNestedScrollingEnabled(false);

        return view;
    }
}
