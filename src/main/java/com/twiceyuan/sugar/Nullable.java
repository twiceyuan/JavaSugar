package com.twiceyuan.sugar;

import com.twiceyuan.sugar.func.Callback;

/**
 * Created by twiceYuan on 4/29/16.
 * <p>
 * 可能为 Null
 */
public final class Nullable<T> {

    T mT;

    private Nullable(T t) {
        mT = t;
    }

    public static <T> Nullable<T> of(T t) {
        return new Nullable<>(t);
    }

    /**
     * 对象不为 null 时才会回调
     */
    public Nullable<T> ifNotNull(Callback<T> call) {
        if (mT != null && call != null) {
            call.call(mT);
        }
        return this;
    }

    /**
     * 对象为 null 才会回调(一个 Null 对象)
     */
    public Nullable<T> ifNull(Callback<Null> call) {
        if (mT == null && call != null) {
            call.call(sSingleNull);
        }
        return this;
    }

    /**
     * 不再检查直接获得该对象
     */
    public T getNullable() {
        return mT;
    }

    /**
     * 孤独的 Null
     */
    private static final Null sSingleNull = new Null();

    public static Null nullObject() {
        return sSingleNull;
    }

    public static class Null {
        private Null() {
        }
    }
}
