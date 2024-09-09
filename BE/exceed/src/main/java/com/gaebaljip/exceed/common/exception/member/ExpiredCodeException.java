package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class ExpiredCodeException extends EatCeedException {
    public static EatCeedException EXECPTION = new ExpiredCodeException();

    private ExpiredCodeException() {
        super(MemberError.INVALID_CODE);
    }
}
