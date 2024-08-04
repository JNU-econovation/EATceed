package com.gaebaljip.exceed.common.exception.nutritionist;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MonthDaysSizeMismatchException extends EatCeedException {

    public static EatCeedException EXECPTION = new MonthDaysSizeMismatchException();

    private MonthDaysSizeMismatchException() {
        super(NutritionistError.NOT_SAME_SIZE);
    }
}
