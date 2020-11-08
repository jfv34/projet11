package com.vincler.jf.projet11.presentation.menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.LanguageEnum;

public class MenuFragmentViewModel extends ViewModel {

    MutableLiveData<LanguageEnum> language = new MutableLiveData<>();
    MutableLiveData<Boolean> isFrench = new MutableLiveData<>();
    MutableLiveData<Boolean> isEnglish = new MutableLiveData<>();
    MutableLiveData<Boolean> isSpain = new MutableLiveData<>();

}
