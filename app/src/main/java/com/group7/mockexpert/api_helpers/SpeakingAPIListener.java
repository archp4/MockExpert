package com.group7.mockexpert.api_helpers;

import com.group7.mockexpert.models.SpeakingQuestion;

public interface SpeakingAPIListener{
    void onReceive(SpeakingQuestion question);
    void onError(String errorMessage);
}
