package com.vincler.jf.projet11.presentation.resultGame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareScoreViewModel extends ViewModel {

    MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    MutableLiveData<String> mailAddress = new MutableLiveData<>();

}