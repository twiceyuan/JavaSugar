package com.twiceyuan.sugar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * List 转换器
 */
public class Converter {

    public static <From, Target> List<Target> convert(List<From> froms, ValueConverter<From, Target> converter) {
        List<Target> targets = new ArrayList<>();
        for (From from : froms) {
            targets.add(converter.convert(from));
        }
        return targets;
    }

    public interface ValueConverter<From, Target> {
        Target convert(From from);
    }
}
