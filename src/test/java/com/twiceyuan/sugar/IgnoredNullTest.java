package com.twiceyuan.sugar;

import org.junit.Test;

/**
 * Created by twiceYuan on 27/09/2016.
 * <p>
 * 测试 IgnoredNull
 */
@SuppressWarnings("Convert2MethodRef")
public class IgnoredNullTest {

    @Test
    public void test() {
        final String testString = "I'm a bug!";

        IgnoredNull.of(new IgnoredNull.ValueProvider<Object>() {
            @Override
            public Object provide() {
                return IgnoredNullTest.this.buildValue(testString).body.body.body;
            }
        }).ifPresent(new IgnoredNull.ValueCallback<Object>() {
            @Override
            public void call(Object message) {
                // 检测所有引用非空, 且值不为空
                System.out.println(message);
            }
        }).ifNull(new IgnoredNull.NullCallback() {
            @Override
            public void onNull() {
                // 该值为空或引用为空
                System.out.println("该值为空或引用为空");
            }
        });

        IgnoredNull.of(new IgnoredNull.ValueProvider<Object>() {
            @Override
            public Object provide() {
                return IgnoredNullTest.this.buildBug(testString).body.body.body;
            }
        }).ifPresent(new IgnoredNull.ValueCallback<Object>() {
            @Override
            public void call(Object message) {
                // 检测所有引用非空, 且值不为空
                System.out.println(message);
            }
        }).ifNull(new IgnoredNull.NullCallback() {
            @Override
            public void onNull() {
                // 该值为空或引用为空
                System.out.println("该值为空或引用为空");
            }
        });
    }

    private Bug<Bug<Bug<String>>> buildValue(String bugMessage) {
        Bug<Bug<Bug<String>>> bug = new Bug<>();
        Bug<Bug<String>> bugChild = new Bug<>();
        Bug<String> bugMessageFoo = new Bug<>();

        bug.body = bugChild;
        bugChild.body = bugMessageFoo;
        bugMessageFoo.body = bugMessage;

        return bug;
    }

    private Bug<Bug<Bug<String>>> buildBug(String bugMessage) {
        Bug<Bug<Bug<String>>> bug = new Bug<>();
        Bug<Bug<String>> bugChild = new Bug<>();
        Bug<String> bugMessageFoo = new Bug<>();

        bug.body = bugChild;
        bugChild.body = null; // a NPE
        bugMessageFoo.body = bugMessage;

        return bug;
    }

    private static class Bug<T> {
        T body;
    }
}
