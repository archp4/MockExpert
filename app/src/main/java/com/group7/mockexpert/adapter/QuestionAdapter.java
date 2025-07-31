package com.group7.mockexpert.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group7.mockexpert.R;
import com.group7.mockexpert.models.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private Context context;
    private Map<String, List<Question>> questionMap;




    public QuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestionText.setText(question.getQuestion());
        holder.tvAnswerType.setText(String.format("Type: %s", question.getType()));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionText, tvAnswerType;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            tvAnswerType = itemView.findViewById(R.id.tv_answer_type);
        }
    }



    public static Map<String, List<Question>> splitQuestionsByType(List<Question> questions) {
        Map<String, List<Question>> splitMap = new HashMap<>();
        for (Question q : questions) {
            String type = q.getType();
            splitMap.putIfAbsent(type, new ArrayList<>());
            splitMap.get(type).add(q);
        }
        return splitMap;
    }
}

