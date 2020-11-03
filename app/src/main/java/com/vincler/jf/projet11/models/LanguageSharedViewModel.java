package com.vincler.jf.projet11.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LanguageSharedViewModel extends ViewModel {

    public MutableLiveData<String> language = new MutableLiveData<>();
}
