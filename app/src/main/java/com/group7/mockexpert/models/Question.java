package com.group7.mockexpert.models;

import java.util.Map;
import java.util.List;

public class Question {
    private int number;
    private String question;
    private List<String> options;
    private Object answer;
    private String type;
    private String word_limit;
    private Map<String, String> headings_options;
    private List<String> choices;

    private String userAnswer;


    public boolean isCorrect(){
        if (userAnswer == null)
            return false;
        return userAnswer.equals(answer);
    }
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getType() {
        return type;
    }

    public String getWord_limit() {
        return word_limit;
    }

    public Map<String, String> getHeadings_options() {
        return headings_options;
    }

    public List<String> getChoices() {
        return choices;
    }

    // Handle both single and multiple answers
    public List<String> getAnswerList() {
        if (answer instanceof String) {
            return List.of((String) answer);
        } else if (answer instanceof List<?>) {
            return (List<String>) answer;
        }
        return List.of();
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWord_limit(String word_limit) {
        this.word_limit = word_limit;
    }

    public void setHeadings_options(Map<String, String> headings_options) {
        this.headings_options = headings_options;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public Object getAnswerRaw() {
        return answer;
    }
}
