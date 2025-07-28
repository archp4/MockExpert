package com.group7.mockexpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.R;
import com.group7.mockexpert.models.Passage;

import java.util.List;

public class PassageAdapter extends RecyclerView.Adapter<PassageAdapter.PassageViewHolder> {

    private List<Passage> passageList;
    private Context context;

    public PassageAdapter(Context context, List<Passage> passageList) {
        this.context = context;
        this.passageList = passageList;
    }

    @NonNull
    @Override
    public PassageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pasage, parent, false);
        return new PassageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassageViewHolder holder, int position) {
        Passage passage = passageList.get(position);
        holder.tvTitle.setText(passage.getTitle());
        holder.tvContent.setText(passage.getText());

        // Setup QuestionAdapter
        QuestionAdapter questionAdapter = new QuestionAdapter(context, passage.getQuestions());
        holder.rvQuestions.setLayoutManager(new LinearLayoutManager(context));
        holder.rvQuestions.setAdapter(questionAdapter);

        // Expand/collapse logic (optional)
        holder.rvQuestions.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            if (holder.rvQuestions.getVisibility() == View.VISIBLE) {
                holder.rvQuestions.setVisibility(View.GONE);
            } else {
                holder.rvQuestions.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return passageList.size();
    }

    public static class PassageViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        RecyclerView rvQuestions;

        public PassageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_passage_title);
            tvContent = itemView.findViewById(R.id.tv_passage_content);
            rvQuestions = new RecyclerView(itemView.getContext());
            ((ViewGroup) itemView).addView(rvQuestions); // dynamically add nested RecyclerView
        }
    }
}
