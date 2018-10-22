package com.agh.hydra.common.util;


import com.agh.hydra.common.model.ValueObject;
import lombok.experimental.UtilityClass;


import static java.util.Optional.ofNullable;

@UtilityClass
public class ValueObjectUtil {

    public static <T> T getValue(ValueObject<T> object){
        return ofNullable(object).map(ValueObject::getValue).orElse(null);
    }
}
