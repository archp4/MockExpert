package com.group7.mockexpert.models;

import java.util.Map;
import java.util.List;

public class Question {
    private int number;
    private String question;
    private List<String> options;
    private Object answer; // Can be String or List<String>
    private String type;
    private String word_limit;
    private Map<String, String> headings_options;
    private List<String> choices;

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

    public Object getAnswerRaw() {
        return answer;
    }
}
