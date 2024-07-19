package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidAgeException extends EatCeedException {

    public static EatCeedException EXECPTION = new InvalidAgeException();

    private InvalidAgeException() {
        super(MemberError.INVALID_AGE);
    }
}
