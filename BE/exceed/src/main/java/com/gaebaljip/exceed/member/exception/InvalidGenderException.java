package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidGenderException extends EatCeedException {

    public static EatCeedException EXECPTION = new InvalidGenderException();

    private InvalidGenderException() {
        super(MemberError.INVALID_GENDER);
    }
}
