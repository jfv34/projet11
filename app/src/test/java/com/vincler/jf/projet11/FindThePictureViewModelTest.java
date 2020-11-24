package com.vincler.jf.projet11;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.presentation.findThePicture.FindThePictureViewModel;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindThePictureViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void When_changeBorderPictureColor_for_green_in_position_2_borderPictureColor_is_updated() {

        FindThePictureViewModel findThePictureViewModel = new FindThePictureViewModel();

        findThePictureViewModel.changeBorderPictureColor(ColorEnum.GREEN, 2);
        ColorEnum borderColor = findThePictureViewModel.borderPictureColor.getValue().getBorderColor();
        int positionPicture = findThePictureViewModel.borderPictureColor.getValue().getPosition();
        Assertions.assertThat(borderColor).isEqualByComparingTo(ColorEnum.GREEN);
        Assertions.assertThat(positionPicture).isEqualTo(2);
    }
}