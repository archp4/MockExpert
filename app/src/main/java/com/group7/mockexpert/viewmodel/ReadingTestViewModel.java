package com.group7.mockexpert.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.Question;

import java.util.List;

public class ReadingTestViewModel extends ViewModel {
    private final MutableLiveData<List<Passage>> passagesLiveData = new MutableLiveData<>();
    public LiveData<List<Passage>> getPassagesLiveData() {
        return passagesLiveData;
    }
    public void setPassages(List<Passage> passages) {
        passagesLiveData.setValue(passages);
    }

    public int calculateScore() {
        int score = 0;
        if (passagesLiveData.getValue() != null){
            for (Passage passage : passagesLiveData.getValue()) {
                for (Question q : passage.getQuestions()) {
                    if (q.isCorrect()) {
                        score++;
                    }
                }
            }
        }
        return score;
    }
}
