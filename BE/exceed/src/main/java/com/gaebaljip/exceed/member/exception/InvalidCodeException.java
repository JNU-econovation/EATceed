package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidCodeException extends EatCeedException {
    public static EatCeedException EXECPTION = new InvalidCodeException();

    private InvalidCodeException() {
        super(MemberError.INVALID_CODE);
    }
}
