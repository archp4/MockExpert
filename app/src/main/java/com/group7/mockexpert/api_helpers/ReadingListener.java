package com.group7.mockexpert.api_helpers;

import com.group7.mockexpert.models.ReadingTestResponse;

public interface ReadingListener {
    public void onReceive(ReadingTestResponse incomingData);
    void onError(String errorMessage);
}
