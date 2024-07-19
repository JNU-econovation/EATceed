package com.gaebaljip.exceed.common.exception.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class NotSameDateException extends EatCeedException {
    public static EatCeedException EXECPTION = new NotSameDateException();

    private NotSameDateException() {
        super(MealError.NOT_SAME_DATE);
    }
}
