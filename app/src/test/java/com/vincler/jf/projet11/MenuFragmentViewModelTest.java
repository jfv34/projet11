package com.vincler.jf.projet11;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.presentation.menu.MenuFragmentViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MenuFragmentViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_changeLanguage_for_ENGLISH_language_is_updated() {

        MenuFragmentViewModel menuFragmentViewModel = new MenuFragmentViewModel();

        menuFragmentViewModel.changeLanguage(LanguageEnum.ENGLISH);
        LanguageEnum language = menuFragmentViewModel.language.getValue();

        Assertions.assertThat(language).isEqualTo(LanguageEnum.ENGLISH);
    }
}