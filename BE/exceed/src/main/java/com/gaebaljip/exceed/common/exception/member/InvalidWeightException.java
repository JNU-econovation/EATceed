package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidWeightException extends EatCeedException {
    public static EatCeedException EXECPTION = new InvalidWeightException();

    private InvalidWeightException() {
        super(MemberError.INVALID_WEIGHT);
    }
}
