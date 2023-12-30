package com.gaebaljip.exceed.food.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class FoodNotFoundException extends NoSuchElementException {

    private final MessageCode messageCode;

    public FoodNotFoundException() {
        super(MessageCode.INVALID_FOOD.getValue());
        this.messageCode = MessageCode.INVALID_FOOD;
    }
}
