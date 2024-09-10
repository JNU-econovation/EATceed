package com.gaebaljip.exceed.common.exception.food;

import com.gaebaljip.exceed.common.exception.EatCeedException;

public class CannotDeleteOthersFoodException extends EatCeedException {

    public static EatCeedException Exception = new CannotDeleteOthersFoodException();

    private CannotDeleteOthersFoodException() {
        super(FoodError.CANNOT_DELETE_OTHERS_FOOD);
    }
}
