package com.gaebaljip.exceed.common.exception.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidMultipleAndGException extends EatCeedException {
    public static final EatCeedException EXCEPTION = new InvalidMultipleAndGException();

    private InvalidMultipleAndGException() {
        super(MealError.INVALID_MULTIPLE_AND_G);
    }
}
