package com.twiceyuan.sugar;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * 更简洁地判断多个变量没有 null 或者空引用
 */
public class CheckNull {

    private boolean isAllNotNull = false;

    public CheckNull(boolean isAllNotNull) {
        this.isAllNotNull = isAllNotNull;
    }

    public static CheckNull of(CheckerProvider provider) {
        try {
            return provider.call(object -> {
                for (Object o : object) {
                    if (o == null) {
                        return new CheckNull(false);
                    }
                }
                return new CheckNull(true);
            });
        } catch (Exception e) {
            return new CheckNull(false);
        }
    }

    public CheckNull ifPresent(NotNullCallback callback) {
        if (callback != null && isAllNotNull) {
            callback.call();
        }
        return this;
    }

    public CheckNull ifHasNull(HasNullCallback callback) {
        if (callback != null && (!isAllNotNull)) {
            callback.call();
        }
        return this;
    }

    public interface CheckerProvider {
        CheckNull call(NullChecker checker);
    }

    public interface NullChecker {
        CheckNull check(Object... object);
    }

    public interface NotNullCallback {
        void call();
    }

    public interface HasNullCallback {
        void call();
    }
}
