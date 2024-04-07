package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.MessageCode;
import com.gaebaljip.exceed.common.exception.BaseErrorCode;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.meal.adapter.in.EatMealController;
import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends EatCeedException {
    public static EatCeedException EXECPTION = new ExtentionNotAllowedException();

    private ExtentionNotAllowedException() {
        super(MealErrorCode.EXTENTION_NOT_ALLOWED);
    }
}
