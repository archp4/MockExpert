package com.group7.mockexpert.api_helpers;

import com.group7.mockexpert.models.Section;

import java.util.List;

public interface ListeningListener {
    public void onResponse(List<Section> sectionList);
    public void onError(String message);
}
