package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class NotSameDateException extends IllegalArgumentException{

    private final MessageCode messageCode;

    public NotSameDateException() {
        super(MessageCode.NOT_SAME_DATE.getValue());
        this.messageCode = MessageCode.NOT_SAME_DATE;
    }
}
