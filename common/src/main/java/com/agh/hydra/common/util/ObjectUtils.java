package com.agh.hydra.common.util;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@UtilityClass
public class ObjectUtils {

    public static <T, E> E getOrNull(T source, Function<T, E> mapper) {
        return getOrDefault(source, mapper, null);
    }

    public static <T, E> E getOrDefault(T source, Function<T, E> mapper, E other) {
        return ofNullable(source).map(mapper).orElse(other);
    }
}
