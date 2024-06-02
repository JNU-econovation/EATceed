package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidMultipleException extends EatCeedException {

    public static EatCeedException EXECPTION = new InvalidMultipleException();

    private InvalidMultipleException() {
        super(MealError.INVALID_MULTIPLE);
    }
}
