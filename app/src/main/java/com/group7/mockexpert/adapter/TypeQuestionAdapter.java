package com.group7.mockexpert.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.mockexpert.R;
import com.group7.mockexpert.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MATCHING_HEADINGS = 0;
    private static final int TYPE_SENTENCE_COMPLETION = 1;
    private static final int TYPE_MULTIPLE_CHOICE = 2;
    private static final int TYPE_TRUE_FALSE = 3;
    private static final int TYPE_SHORT_ANSWER = 4;
    private final List<Question> questionList;
    private String sentenceQuestion;
    private final Context context;

    public TypeQuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_MATCHING_HEADINGS:
                View matchingView = inflater.inflate(R.layout.item_question_matching_headings, parent, false);
                return new MatchingHeadingsViewHolder(matchingView);
            case TYPE_SENTENCE_COMPLETION:
                View sentenceView = inflater.inflate(R.layout.item_question_sentence_completion, parent, false);
                return new SentenceCompletionViewHolder(sentenceView);
            case TYPE_MULTIPLE_CHOICE:
                View multipleChoiceView = inflater.inflate(R.layout.item_question_multiple_choice, parent, false);
                return new MultipleChoiceViewHolder(multipleChoiceView);
            case TYPE_TRUE_FALSE:
                View trueFalseView = inflater.inflate(R.layout.item_question_true_false, parent, false);
                return new TrueFalseViewHolder(trueFalseView);
            default:
                View defaultView = inflater.inflate(R.layout.item_question_answer, parent, false);
                return new QuestionViewHolder(defaultView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_MATCHING_HEADINGS:
                ((MatchingHeadingsViewHolder) holder).bind(question);
                break;
            case TYPE_SENTENCE_COMPLETION:
                ((SentenceCompletionViewHolder) holder).bind(question);
                break;
            case TYPE_MULTIPLE_CHOICE:
                ((MultipleChoiceViewHolder) holder).bind(question);
                break;
            case TYPE_TRUE_FALSE:
                ((TrueFalseViewHolder) holder).bind(question);
                break;
            default:
                if (sentenceQuestion == null) {
                    ((QuestionViewHolder) holder).bind(question, false);
                    sentenceQuestion = question.getQuestion();
                }
                else{
                    ((QuestionViewHolder) holder).bind(question, true);
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = questionList.get(position).getType();
        switch (type.toLowerCase()) {
            case "matching headings":
                return TYPE_MATCHING_HEADINGS;
            case "sentence completion":
                return TYPE_SENTENCE_COMPLETION;
            case "multiple choice":
                return TYPE_MULTIPLE_CHOICE;
            case "true/false/not given":
            case "yes/no/not given":
                return TYPE_TRUE_FALSE;
            case "short answer":
                return TYPE_SHORT_ANSWER;
            default:
                return -1;
        }
    }
    @SuppressLint("SetTextI18n")
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText;
        EditText etUserAnswer;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            etUserAnswer = itemView.findViewById(R.id.et_user_answer);
        }

        public void bind(Question question, boolean isRepeat) {
            tvQuestionNumber.setText("Question " + question.getNumber());
            if (!isRepeat)
                tvQuestionText.setText(question.getQuestion());
            etUserAnswer.setText(question.getUserAnswer() == null ? "" : question.getUserAnswer());
            etUserAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    question.setUserAnswer(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
    static class MatchingHeadingsViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText;
        Spinner spinnerHeadings;

        public MatchingHeadingsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            spinnerHeadings = itemView.findViewById(R.id.spinner_headings);
        }
        @SuppressLint("SetTextI18n")
        public void bind(Question question) {
            tvQuestionNumber.setText("Question " + question.getNumber());
            tvQuestionText.setText(question.getQuestion());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    itemView.getContext(),
                    android.R.layout.simple_spinner_item,
                    new ArrayList<>(question.getHeadings_options().values()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHeadings.setAdapter(adapter);
            spinnerHeadings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    question.setUserAnswer(adapter.getItem(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    static class SentenceCompletionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText, tvWordLimit;
        EditText etUserAnswer;

        public SentenceCompletionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number_sentence);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text_sentence);
            etUserAnswer = itemView.findViewById(R.id.et_user_answer_sentence);
            tvWordLimit = itemView.findViewById(R.id.tv_word_limit_sentence);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Question question) {
            tvQuestionNumber.setText("Question " + question.getNumber());
            tvQuestionText.setText(question.getQuestion());
            tvWordLimit.setText("Word limit: " + (question.getWord_limit() == null ? "N/A" : question.getWord_limit()));
            etUserAnswer.setText(question.getUserAnswer() == null ? "" : question.getUserAnswer());
            etUserAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    question.setUserAnswer(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    static class MultipleChoiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText;
        RadioGroup rgOptions;

        public MultipleChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            rgOptions = itemView.findViewById(R.id.rg_options);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Question question) {
            tvQuestionNumber.setText("Question " + question.getNumber());
            tvQuestionText.setText(question.getQuestion());
            rgOptions.removeAllViews();
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                RadioButton rb = new RadioButton(itemView.getContext());
                rb.setText(options.get(i));
                rb.setId(i);
                rgOptions.addView(rb);
            }
            rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton rbSelected = group.findViewById(checkedId);
                if (rbSelected != null) {
                    question.setUserAnswer(rbSelected.getText().toString());
                }
            });


        }
    }
    @SuppressLint("SetTextI18n")
    static class TrueFalseViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber, tvQuestionText;
        RadioGroup rgTrueFalse;

        public TrueFalseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tv_question_number);
            tvQuestionText = itemView.findViewById(R.id.tv_question_text);
            rgTrueFalse = itemView.findViewById(R.id.rg_true_false);
        }

        public void bind(Question question) {
            tvQuestionNumber.setText("Question " + question.getNumber());
            tvQuestionText.setText(question.getQuestion());
            rgTrueFalse.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton rbSelected = group.findViewById(checkedId);
                if (rbSelected != null) {
                    question.setUserAnswer(rbSelected.getText().toString());
                }
            });

        }
    }
}
