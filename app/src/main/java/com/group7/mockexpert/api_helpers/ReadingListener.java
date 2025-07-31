package com.group7.mockexpert.api_helpers;

import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.ReadingTestResponse;

import java.util.List;

public interface ReadingListener {
    public void onReceive(List<Passage> passageList);
    void onError(String errorMessage);
}
