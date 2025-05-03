package com.onur.retail.util;

import java.math.BigDecimal;
import java.util.Arrays;

public class Validate {
    public static void validateString(String... values) {
        if (!Arrays.stream(values).allMatch(value -> value != null && !value.isBlank())) {
            throw new IllegalArgumentException("Provided value(s) must be non-null, non-blank");
        }
    }

    public static void validatePositiveInteger(Integer... values) {
        if (!Arrays.stream(values).allMatch(value -> value != null && value >= 0)) {
            throw new IllegalArgumentException("Provided value(s) must be non-null, and bigger or equal to 0");
        }
    }

    public static void validateBigDecimal(BigDecimal... values) {
        if (!Arrays.stream(values).allMatch(value -> value != null && value.compareTo(BigDecimal.ZERO) > 0)) {
            throw new IllegalArgumentException("Provided price(s) must be non-null, and bigger than 0");
        }
    }
}
