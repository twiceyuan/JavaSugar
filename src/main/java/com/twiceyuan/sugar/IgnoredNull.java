package com.twiceyuan.sugar;

/**
 * Created by twiceYuan on 27/09/2016.
 * <p>
 * 忽略空引用的对象, 类似 Optional, 传入获取值的函数来封装空引用检查
 * 通过对值的 Provider 进行 Catch NPE 操作, 使调用过程不再关注空引用
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class IgnoredNull<T> {

    private T mCacheValue;

    /**
     * 允许取值过程中所有引用可以为 null, 包括最终值
     *
     * @param provider 传入需要检测的获取值的函数
     * @param <T>      该值的类型
     * @return 该值的 IgnoredNull 对象
     */
    public static <T> IgnoredNull<T> of(ValueProvider<T> provider) {
        try {
            return new IgnoredNull<>(provider.provide());
        } catch (NullPointerException e) {
            return new IgnoredNull<>(null);
        }
    }

    private IgnoredNull(T t) {
        mCacheValue = t;
    }

    public IgnoredNull<T> ifPresent(ValueCallback<T> callback) {
        if (mCacheValue != null) {
            callback.call(mCacheValue);
        }
        return this;
    }

    public IgnoredNull<T> ifNull(NullCallback callback) {
        if (mCacheValue == null) {
            callback.onNull();
        }
        return this;
    }

    public interface ValueProvider<T> {
        T provide();
    }

    public interface ValueCallback<T> {
        void call(T t);
    }

    public interface NullCallback {
        void onNull();
    }
}
