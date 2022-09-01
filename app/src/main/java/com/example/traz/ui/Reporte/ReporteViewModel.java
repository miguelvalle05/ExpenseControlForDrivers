package com.example.traz.ui.Reporte;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReporteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReporteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reporte fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}