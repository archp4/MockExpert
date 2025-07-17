package com.group7.mockexpert.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReadingPassageQuestion implements Serializable {
    private int number;
    private String question;
    private String type;
    private String answer;
    private List<String> options;

    public ReadingPassageQuestion(int number, String question, String type, String answer) {
        this.number = number;
        this.question = question;
        this.type = type;
        this.answer = answer;

    }

    public ReadingPassageQuestion(int number, String question, String type, String answer, List<String> options) {
        this(number, question, type, answer);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

}