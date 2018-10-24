package com.agh.hydra.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.RandomStringUtils;

@UtilityClass
public class RandomUtils {

    public static String randomString(int size){
        return RandomStringUtils.random(size);
    }
}
