package com.group7.mockexpert.models;

import java.util.List;

public class SpeakingQuestion {
    private int questionPart;
    private List<String> questions;

    public int getQuestionPart() {
        return questionPart;
    }

    public void setQuestionPart(int questionPart) {
        this.questionPart = questionPart;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SpeakingQuestion{" +
                "questionPart=" + questionPart +
                ", questions=" + questions +
                '}';
    }
}
