package com.gaebaljip.exceed.nutritionist.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MealNotFoundException extends EatCeedException {

    public static EatCeedException EXECPTION = new MealNotFoundException();

    private MealNotFoundException() {
        super(NutritionistError.INVALID_MEAL);
    }
}
