package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends EatCeedException {
    public static EatCeedException EXECPTION = new MemberNotFoundException();

    private MemberNotFoundException() {
        super(MemberError.INVALID_MEMBER);
    }
}
