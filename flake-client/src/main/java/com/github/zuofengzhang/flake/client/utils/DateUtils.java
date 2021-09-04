package com.github.zuofengzhang.flake.client.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DAY_ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static int dayId(LocalDate localDate) {
        return Integer.parseInt(localDate.format(DAY_ID_FORMATTER));
    }

    public static Pair<Long, Long> lastDayRangeOfDayId(int dayId) {
        LocalDate localDate = LocalDate.parse(String.valueOf(dayId), DAY_ID_FORMATTER);
        localDate = localDate.minusDays(1);
        long startTimeInMillis = LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endTimeInMillis   = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Pair.of(startTimeInMillis, endTimeInMillis);
    }
}
