package com.gaebaljip.exceed.common.exception.nutritionist;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class SameYearAndMonthException extends EatCeedException {

    public static EatCeedException EXECPTION = new SameYearAndMonthException();

    private SameYearAndMonthException() {
        super(NutritionistError.NOT_SAME_YEAR_MONTH);
    }
}
