package com.gaebaljip.exceed.nutritionist.exception;

import com.gaebaljip.exceed.common.MessageCode;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class MealNotFoundException extends EatCeedException {

    public static EatCeedException EXECPTION = new MealNotFoundException();

    private MealNotFoundException() {
        super(NutritionistErrorCode.INVALID_MEAL);

    }
}
