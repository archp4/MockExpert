package com.group7.mockexpert.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.R;
import com.group7.mockexpert.models.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PassageQuestionAdapter extends RecyclerView.Adapter<PassageQuestionAdapter.PassageQuestionViewHolder> {

    private final Map<String, List<Question>> passageQuestionsMap;
    private final List<String> questionTypes; // to preserve order
    private final Context context;

    public PassageQuestionAdapter(Context context, List<Question> passageQuestionsList) {
        this.context = context;
        this.passageQuestionsMap = splitQuestionsByType(passageQuestionsList);
        this.questionTypes = new ArrayList<>(passageQuestionsMap.keySet());
    }

    @NonNull
    @Override
    public PassageQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passage_list_question, parent, false);
        return new PassageQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassageQuestionViewHolder holder, int position) {
        String type = questionTypes.get(position);
        List<Question> questions = passageQuestionsMap.get(type);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setAdapter(new TypeQuestionAdapter(context, questions));

    }

    @Override
    public int getItemCount() {
        return questionTypes.size(); // number of unique question types
    }

    public static class PassageQuestionViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public PassageQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_passage_question);
        }
    }

    public static Map<String, List<Question>> splitQuestionsByType(List<Question> questions) {
        Map<String, List<Question>> splitMap = new LinkedHashMap<>();
        for (Question q : questions) {
            String type = q.getType();
            if (!splitMap.containsKey(type)) {
                splitMap.put(type, new ArrayList<>());
            }
            splitMap.get(type).add(q);
        }
        return splitMap;
    }
}