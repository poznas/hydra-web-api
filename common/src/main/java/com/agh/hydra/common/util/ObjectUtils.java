package com.agh.hydra.common.util;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@UtilityClass
public class ObjectUtils {

    public static <T, E> E getOrNull(T source, Function<T, E> mapper) {
        return ofNullable(source).map(mapper).orElse(null);
    }
}
