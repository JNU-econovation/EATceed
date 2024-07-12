package com.gaebaljip.exceed.common.exception.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;

public class InvalidGException extends EatCeedException {

    public static final EatCeedException EXCEPTION = new InvalidGException();

    public InvalidGException() {
        super(MealError.INVALID_G);
    }
}
