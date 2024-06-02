package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends EatCeedException {
    public static EatCeedException EXECPTION = new ExtentionNotAllowedException();

    private ExtentionNotAllowedException() {
        super(MealError.EXTENTION_NOT_ALLOWED);
    }
}
