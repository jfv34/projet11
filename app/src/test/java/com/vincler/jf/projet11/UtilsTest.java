package com.vincler.jf.projet11;

import com.vincler.jf.projet11.utils.Utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UtilsTest {

    @Test
    public void when_argument_for_random_is_3_the_length_of_String_returned_is_3() {
        String randomLetters = Utils.random(3);
        Assert.assertEquals(3, randomLetters.length());
    }

    @Test
    public void when_arument_for_getListRandom_is_8_the_size_of_list_returned_is_8() {
        List<Integer> randomList = Utils.getListRandom(8);
        Assert.assertEquals(8, randomList.size());
    }
}