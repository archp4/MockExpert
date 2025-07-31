package com.group7.mockexpert.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group7.mockexpert.models.Passage;

import java.util.List;

public class ReadingTestViewModel extends ViewModel {
    private final MutableLiveData<List<Passage>> passagesLiveData = new MutableLiveData<>();
    public LiveData<List<Passage>> getPassagesLiveData() {
        return passagesLiveData;
    }
    public void setPassages(List<Passage> passages) {
        passagesLiveData.setValue(passages);
    }

}
