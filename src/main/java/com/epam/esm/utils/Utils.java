package com.epam.esm.utils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    public static <T> Map<String, Object> getUpdateDBMapping(T obj, Map<String, Function<T, Object>> getters){
        return getters
                .entrySet()
                .stream()
                .parallel()
                .filter(x -> x.getValue().apply(obj) != null)
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().apply(obj)));
    }
}
