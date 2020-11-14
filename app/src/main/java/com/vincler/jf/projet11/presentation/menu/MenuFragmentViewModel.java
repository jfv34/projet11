package com.vincler.jf.projet11.presentation.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.LanguageEnum;

public class MenuFragmentViewModel extends ViewModel {

    MutableLiveData<LanguageEnum> language = new MutableLiveData<>(LanguageEnum.ENGLISH);
    LiveData<Boolean> isFrench = Transformations.map(language, input -> input==LanguageEnum.FRENCH);
    LiveData<Boolean> isEnglish =Transformations.map(language, input -> input==LanguageEnum.ENGLISH);
    LiveData<Boolean> isSpanish = Transformations.map(language, input -> input==LanguageEnum.SPANISH);

    public void changeLanguage(LanguageEnum languageEnum){
        language.setValue(languageEnum);
    }

}
