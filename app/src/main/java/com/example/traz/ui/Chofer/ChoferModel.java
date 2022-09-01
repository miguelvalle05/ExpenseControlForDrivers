package com.example.traz.ui.Chofer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChoferModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChoferModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is chofer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
