package com.group7.mockexpert.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.R;
import com.group7.mockexpert.models.Question;

import java.util.List;

public class TypeQuestionAdapter extends RecyclerView.Adapter<TypeQuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private Context context;

    public TypeQuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }


    @NonNull
    @Override
    public TypeQuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_answer, parent, false);
        return new TypeQuestionAdapter.QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeQuestionAdapter.QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestionNumber.setText(String.format("Question %d", question.getNumber()));
        holder.tvQuestionText.setText(question.getQuestion());
        Log.d("TypeQuestionAdapter", "Total Questions for this passage: " + questionList.size());

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText;
        EditText etUserAnswer;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            etUserAnswer = itemView.findViewById(R.id.et_user_answer);
        }

    }
}
