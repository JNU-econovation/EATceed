package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidHeightException extends EatCeedException {

    public static EatCeedException EXECPTION = new InvalidHeightException();

    private InvalidHeightException() {
        super(MemberError.INVALID_HEIGHT);
    }
}
