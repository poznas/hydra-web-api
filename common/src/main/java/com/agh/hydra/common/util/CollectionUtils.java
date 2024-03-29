package com.agh.hydra.common.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@UtilityClass
public class CollectionUtils {

     public static <T, E> List<T> mapList(Collection<E> collection, Function<E, T> function){
         return ofNullable(collection).orElse(emptyList()).stream().map(function).collect(toList());
     }

    public static <T, E> Set<T> mapSet(Collection<E> collection, Function<E, T> function){
        return ofNullable(collection).orElse(emptyList()).stream().map(function).collect(toSet());
    }

    public static boolean haveSameElements(Collection<?> a, Collection<?> b) {
         return nonNull(a) && nonNull(b) && a.size() == b.size() && a.containsAll(b) && b.containsAll(a);
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... array) {
         return ofNullable(array).map(Stream::of).map(stream -> stream.collect(toSet())).orElse(emptySet());
    }
}
