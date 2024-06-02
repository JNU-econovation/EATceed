package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InsufficientMealsException extends EatCeedException {
    public static EatCeedException EXECPTION = new InsufficientMealsException();

    private InsufficientMealsException() {
        super(MealError.INSUFFICIENT_MEALS);
    }
}
