package com.github.zuofengzhang.flake.client.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class FlakeDateUtil {

    private static final DateTimeFormatter YEAR_DAY_ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DAY_ID_FORMATTER = DateTimeFormatter.ofPattern("MMdd");

    public static int dayId(LocalDate localDate) {
        return Integer.parseInt(localDate.format(YEAR_DAY_ID_FORMATTER));
    }

    public static int pureDayId(LocalDate localDate) {
        return Integer.parseInt(localDate.format(DAY_ID_FORMATTER));
    }

    public static Pair<Long, Long> lastDayRangeOfDayId(int dayId) {
        LocalDate localDate = LocalDate.parse(String.valueOf(dayId), YEAR_DAY_ID_FORMATTER);
        localDate = localDate.minusDays(1);
        long startTimeInMillis = LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
        long endTimeInMillis = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
        return Pair.of(startTimeInMillis, endTimeInMillis);
    }

    public static Pair<Long, Long> nearWeekRangeOfDayId(int dayId) {
        LocalDate localDate = LocalDate.parse(String.valueOf(dayId), YEAR_DAY_ID_FORMATTER);
        long endTimeInMillis = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
        localDate = localDate.minusDays(7);
        long startTimeInMillis = LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();

        return Pair.of(startTimeInMillis, endTimeInMillis);
    }

    public static LocalDate fromDayId(Integer expirationDay) {
        Preconditions.checkArgument(expirationDay != null && expirationDay > 100 && expirationDay <= 1231, "dayId is error, dayId: " + expirationDay + ".");
        Pair<Integer, Integer> split = ExpirationUtil.splitExpireDay(expirationDay);
        LocalDate now = LocalDate.now();
        return now.withMonth(split.getKey()).withDayOfMonth(split.getValue());
    }
}
