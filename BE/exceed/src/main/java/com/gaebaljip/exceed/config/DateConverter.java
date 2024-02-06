package com.gaebaljip.exceed.config;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDate;

@UtilityClass
public class DateConverter {
    public static Timestamp toEpochSecond(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    private static LocalDate toLocalDate(Timestamp epochSecond) {
        if (epochSecond == null) {
            return null;
        }

        return epochSecond.toLocalDateTime().toLocalDate();
    }
}
