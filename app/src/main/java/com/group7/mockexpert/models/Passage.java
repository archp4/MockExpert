package com.group7.mockexpert.models;

import java.util.List;

public class Passage {
    private String title;
    private String context;
    private String text;
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public String getText() {
        return text;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

