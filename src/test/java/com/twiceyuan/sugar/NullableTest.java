package com.twiceyuan.sugar;

import com.twiceyuan.sugar.func.Callback;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by twiceYuan on 4/29/16.
 *
 * 测试用例
 */
public class NullableTest {

    @Test
    public void test() {
        buildFuckList().stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String string) {
                NullableTest.this.business(string);
            }
        });
    }

    List<String> buildFuckList() {
        return Arrays.asList(null, "Test!", null, "Test!", null, "Test!", null, "Test!", null, "Test!");
    }

    void business(String string) {
        Nullable.of(string).ifNotNull(new Callback<String>() {
            @Override
            public void call(String s) {
                assert s != null;
            }
        }).ifNull(new Callback<Nullable.Null>() {
            @Override
            public void call(Nullable.Null aNull) {
                assert aNull == Nullable.nullObject();
            }
        });
    }
}
