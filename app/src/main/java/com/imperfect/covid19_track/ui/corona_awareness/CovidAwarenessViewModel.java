package com.imperfect.covid19_track.ui.corona_awareness;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CovidAwarenessViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CovidAwarenessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}