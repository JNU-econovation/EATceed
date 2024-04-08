package com.gaebaljip.exceed.food.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class FoodNotFoundException extends EatCeedException {

    public static EatCeedException Exception = new FoodNotFoundException();

    private FoodNotFoundException() {
        super(FoodErrorCode.INVALID_FOOD);
    }
}
