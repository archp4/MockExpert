package com.group7.mockexpert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group7.mockexpert.adapter.PassageQuestionAdapter;
import com.group7.mockexpert.models.Question;

import java.util.List;


public class QuestionsFragment extends Fragment {

    List<Question> passageOneQuestions, passageTwoQuestions, passageThreeQuestions;

    public QuestionsFragment(List<Question> passageOneQuestions, List<Question> passageTwoQuestions, List<Question> passageThreeQuestions) {
        this.passageOneQuestions = passageOneQuestions;
        this.passageTwoQuestions = passageTwoQuestions;
        this.passageThreeQuestions = passageThreeQuestions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_fragement, container, false);

        RecyclerView rvPassageQuestionOne = view.findViewById(R.id.rv_passage_question_list);
        RecyclerView rvPassageQuestionTwo = view.findViewById(R.id.rv_passage_question_list_two);
        RecyclerView rvPassageQuestionThree = view.findViewById(R.id.rv_passage_question_list_three);

        rvPassageQuestionOne.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionOne.setAdapter(new PassageQuestionAdapter(view.getContext(), passageOneQuestions));
        rvPassageQuestionOne.setNestedScrollingEnabled(false);

        rvPassageQuestionTwo.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionTwo.setAdapter(new PassageQuestionAdapter(view.getContext(), passageTwoQuestions));
        rvPassageQuestionTwo.setNestedScrollingEnabled(false);

        rvPassageQuestionThree.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPassageQuestionThree.setAdapter(new PassageQuestionAdapter(view.getContext(), passageThreeQuestions));
        rvPassageQuestionThree.setNestedScrollingEnabled(false);

        return view;
    }
}
