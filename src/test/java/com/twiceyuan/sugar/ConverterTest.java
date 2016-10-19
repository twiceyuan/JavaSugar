package com.twiceyuan.sugar;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * 转换器 usecase
 */
public class ConverterTest {

    @Test
    public void test() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> converted = Converter.convert(integers, i -> "姓名" + i);
        System.out.println(converted);
    }
}
