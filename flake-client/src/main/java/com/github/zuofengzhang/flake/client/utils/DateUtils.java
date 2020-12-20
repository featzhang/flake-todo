package com.github.zuofengzhang.flake.client.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DAY_ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static int dayId(LocalDate localDate) {
        return Integer.parseInt(localDate.format(DAY_ID_FORMATTER));
    }
}
