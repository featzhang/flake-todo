package com.github.zuofengzhang.flake.client.utils;

import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import static com.github.zuofengzhang.flake.client.controller.ExpireTimeSettingEntity.DEFAULT_DAY_VALUE;
import static com.github.zuofengzhang.flake.client.controller.ExpireTimeSettingEntity.DEFAULT_TIME_VALUE;

public class ExpirationUtil {
    public static String label(Integer expirationDay, Integer expirationTime) {
        int ed = expirationDay == null || expirationDay == 0 || expirationDay == DEFAULT_DAY_VALUE ? 0 : expirationDay;
        int et = expirationTime == null || expirationTime == 0 || expirationTime == DEFAULT_TIME_VALUE ? 0 : expirationTime;

        String s = "";
        if (ed != 0) {
            int month = ed / 100;
            int day = ed % 100;
            s += month + FlakeLabel.MONTH + day + FlakeLabel.DAY;
        }
        if (et != 0) {
            int hour = et / 100;
            int minute = et % 100;
            s += hour + ":" + minute;
        }
        return s;
    }

    public static boolean isDefaultValue(Integer value) {
        return value == null || value == 0 || value == DEFAULT_DAY_VALUE;
    }

    public static boolean isNotDefaultValue(Integer value) {
        return !isDefaultValue(value);
    }

    public static Pair<Integer, Integer> splitExpireDay(Integer value) {
        Preconditions.checkArgument(value != null && value != 0 && value > 100 && value <= 1231, "expirationDay '" + value + "' is valid!");
        int day = value % 100;
        int month = value / 100;
        return Pair.of(month, day);
    }

    public static Pair<Integer, Integer> splitExpireTime(Integer value) {
        Preconditions.checkArgument(value != null && value != 0 && value > 100 && value <= 2359, "expirationTime '" + value + "' is valid!");
        int hour = value % 100;
        int minute = value / 100;
        return Pair.of(minute, hour);
    }
}
