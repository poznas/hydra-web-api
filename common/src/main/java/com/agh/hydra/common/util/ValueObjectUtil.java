package com.agh.hydra.common.util;


import com.agh.hydra.common.model.ValueObject;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

import static com.agh.hydra.common.util.ObjectUtils.getOrNull;

@UtilityClass
public class ValueObjectUtil {

    public static <T> T getValue(ValueObject<T> object){
        return getOrNull(object, ValueObject::getValue);
    }

    public static <T, V extends ValueObject<T>> V valueObject(T value, Function<T, V> mapper){
        return getOrNull(value, mapper);
    }
}
