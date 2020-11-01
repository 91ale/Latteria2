package com.example.latteria.ui.Prodotti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdottiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProdottiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}