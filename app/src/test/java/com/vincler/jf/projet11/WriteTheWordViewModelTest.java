package com.vincler.jf.projet11;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.vincler.jf.projet11.models.WriteTheWordModel;
import com.vincler.jf.projet11.presentation.writetheword.WriteTheWordViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WriteTheWordViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_the_word_is_TEST_getFirstLetter_return_T() {

        WriteTheWordViewModel viewModel = new WriteTheWordViewModel();

        WriteTheWordModel writeTheWordModel = new WriteTheWordModel(
                null,
                "TEST",
                ""
        );

        viewModel.currentModel.setValue(writeTheWordModel);
        String firstLetter = viewModel.getFirstLetter();
        Assertions.assertThat(firstLetter).isEqualTo("T");
    }
}