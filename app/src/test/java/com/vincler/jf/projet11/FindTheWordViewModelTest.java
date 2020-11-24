package com.vincler.jf.projet11;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.presentation.findTheWord.FindTheWordViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindTheWordViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_changeBorderWordColor_for_red_in_position_3_borderWordColor_is_updated() {

        FindTheWordViewModel findTheWordViewModel = new FindTheWordViewModel();

        findTheWordViewModel.changeBorderWordColor(ColorEnum.RED,3);

        ColorEnum wordColor = findTheWordViewModel.borderWordColor.getValue().getBorderColor();
        int position = findTheWordViewModel.borderWordColor.getValue().getPosition();
        Assertions.assertThat(wordColor).isEqualByComparingTo(ColorEnum.RED);
        Assertions.assertThat(position).isEqualTo(3);
    }
}