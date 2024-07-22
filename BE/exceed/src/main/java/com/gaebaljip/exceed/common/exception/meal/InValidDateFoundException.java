package com.gaebaljip.exceed.common.exception.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InValidDateFoundException extends EatCeedException {
    public static EatCeedException EXECPTION = new InValidDateFoundException();

    public InValidDateFoundException() {
        super(MealError.INVALID_DATE_FOUND);
    }
}
