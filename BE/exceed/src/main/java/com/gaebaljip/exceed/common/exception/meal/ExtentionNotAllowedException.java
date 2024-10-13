package com.gaebaljip.exceed.common.exception.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.GlobalError;

import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends EatCeedException {
    public static EatCeedException EXECPTION = new ExtentionNotAllowedException();

    private ExtentionNotAllowedException() {
        super(GlobalError.EXTENTION_NOT_ALLOWED);
    }
}
