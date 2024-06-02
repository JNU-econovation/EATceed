package com.gaebaljip.exceed.config;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.experimental.UtilityClass;

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
