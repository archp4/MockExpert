package com.group7.mockexpert.api_helpers;

public interface SpeakingCompleteListener {
    void onTestResult(String overall,String feedback);
    void onError(String message);
}
